package status.disabled.unknown.fetcher.api.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import status.disabled.unknown.fetcher.api.ApiConnector;
import status.disabled.unknown.fetcher.api.endpoints.V4;
import status.disabled.unknown.fetcher.api.filler.MatchFiller;
import status.disabled.unknown.fetcher.api.model.ApiMatchDTO;
import status.disabled.unknown.fetcher.api.model.ApiMatchReferenceDTO;
import status.disabled.unknown.fetcher.api.model.ApiMatchlistDto;
import status.disabled.unknown.fetcher.exceptions.ApiBadRequestException;
import status.disabled.unknown.fetcher.exceptions.ApiDownException;
import status.disabled.unknown.fetcher.exceptions.ApiUnauthorizedException;
import status.disabled.unknown.fetcher.exceptions.DataNotfoundException;
import status.disabled.unknown.fetcher.logger.LogService;
import status.disabled.unknown.model.*;
import status.disabled.unknown.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchConnector {

    @Autowired
    private MatchFiller matchFiller;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private GameModeRepository gameModeRepository;

    @Autowired
    private GameTypeRepository gameTypeRepository;

    @Autowired
    private GameSeasonRepository gameSeasonRepository;

    @Autowired
    private GameMapRepository gameMapRepository;

    @Autowired
    private GameLaneRepository gameLaneRepository;

    @Autowired
    private GameQueueRepository gameQueueRepository;

    @Autowired
    private GameRoleRepository gameRoleRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private MatchGameRepository matchGameRepository;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private MatchListRepository matchListRepository;

    @Autowired
    private MatchGameTeamStatsRepository matchGameTeamStatsRepository;

    @Autowired
    private MatchGameTeamRepository matchGameTeamRepository;

    @Autowired
    private MatchGameTeamBanRepository matchGameTeamBanRepository;

    @Autowired
    private MatchGameParticipantRepository matchGameParticipantRepository;

    @Autowired
    private ObjectMapper jacksonMapper;

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private LogService logger;

    public List<MatchList> matchListByAccount(SummonerToken summonerToken) { // WRAPPER
        return this.matchListByAccount(summonerToken, 0L);
    }

    public List<MatchList> matchListByAccount(SummonerToken summonerToken, Long beginIndex) {
        ApiMatchlistDto apiMatchlistDto;
        List<MatchList> matchLists = new ArrayList<>();
        ApiCall apiCall;
        try {
            apiCall = this.apiConnector.get(
                    V4.MATCHLIST_BY_ACCOUNT
                            .replace("{{SUMMONER_ACCOUNT}}", summonerToken.getAccountTokenId())
                            .replace("{{HOST}}", summonerToken.getSummoner().getRegion().getHostName())
                            .replace("{{BEGIN_INDEX}}", beginIndex.toString()),
                    true,
                    summonerToken.getApiKey()
            );
            apiMatchlistDto = this.jacksonMapper.readValue(apiCall.getJson(), new TypeReference<ApiMatchlistDto>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.info("Data not found, got exception");
            return matchLists;
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            e.printStackTrace();
            this.logger.error("ACTION REQUIRED. Malformed URL has thrown a 400 BAD REQUEST CODE. With exception " + e.getMessage());
            return matchLists;
        } catch (Exception e) {

            if (e.getMessage() != null) {
                this.logger.error("Got generic exception" + e.getMessage());
            }
            return matchLists;
        }


        for (ApiMatchReferenceDTO apiMatchReferenceDto : apiMatchlistDto.getMatches()) {
            matchLists.add(this.matchFiller.fillMatchListGame(apiMatchReferenceDto, summonerToken));
        }

        // Iterar todas las partidas, cogiendo como primer resultado la siguiente partida a la última almacenada
        if (apiMatchlistDto.getEndIndex() < apiMatchlistDto.getTotalGames()) {
            this.matchListByAccount(summonerToken, apiMatchlistDto.getEndIndex() + 1);
        }
        return matchLists;
    }

    public MatchGameTimeline matchGameTimeline(MatchGame matchGame) {

        return null;
        //TODO: recuperar timeline GET /lol/match/v4/timelines/by-match/{matchId}
    }

    public MatchGame match(MatchGame matchGame) {
        ApiMatchDTO apiMatchDTO;

        ApiCall apiCall = null;
        try {
            apiCall = this.apiConnector.get(
                    V4.MATCHES.replace("{{GAME_ID}}", matchGame.getGameId().toString())
                            .replace("{{HOST}}", matchGame.getRegion().getHostName()),
                    true
            );
            apiMatchDTO = this.jacksonMapper.readValue(apiCall.getJson(), new TypeReference<ApiMatchDTO>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.info("El match no existe..." + matchGame.getGameId().toString());
            matchGame = this.matchGameRepository.findByGameIdAndRegion(matchGame.getGameId(), matchGame.getRegion());
            if (matchGame != null) {
                matchGame.setRetrieved(true);
                matchGame.setRetrieving(false);
                matchGame.setExpired(true);
                this.matchGameRepository.save(matchGame);
            }
            return matchGame;
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            this.logger.error("ACTION REQUIRED. Malformed URL has thrown a 400 BAD REQUEST CODE. With exception " + e.getMessage());
            return matchGame;
        } catch (Exception e) {

            if (e.getMessage() != null) {
                this.logger.error("Got generic exception" + e.getMessage());
            }
            return matchGame;
        }

        this.matchFiller.fillMatchGame(apiMatchDTO, matchGame.getRegion(), apiCall.getApiKey());

        return matchGame;
    }
}
