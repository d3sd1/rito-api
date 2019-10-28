package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.filler.MatchFiller;
import com.onlol.fetcher.api.model.ApiMatchDTO;
import com.onlol.fetcher.api.model.ApiMatchReferenceDTO;
import com.onlol.fetcher.api.model.ApiMatchlistDto;
import com.onlol.fetcher.api.model.ApiSummonerDTO;
import com.onlol.fetcher.exceptions.ApiBadRequestException;
import com.onlol.fetcher.exceptions.ApiDownException;
import com.onlol.fetcher.exceptions.ApiUnauthorizedException;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.MatchGame;
import com.onlol.fetcher.model.MatchGameTimeline;
import com.onlol.fetcher.model.MatchList;
import com.onlol.fetcher.model.Region;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<MatchList> matchListByAccount(ApiSummonerDTO apiSummonerDTO, Region region) { // WRAPPER
        return this.matchListByAccount(apiSummonerDTO, region, 0L);
    }

    public List<MatchList> matchListByAccount(ApiSummonerDTO apiSummonerDTO, Region region, Long beginIndex) {
        ApiMatchlistDto apiMatchlistDto;
        List<MatchList> matchLists = new ArrayList<>();
        try {
            apiMatchlistDto = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.MATCHLIST_BY_ACCOUNT
                            .replace("{{SUMMONER_ACCOUNT}}", apiSummonerDTO.getAccountId())
                            .replace("{{HOST}}", region.getHostName())
                            .replace("{{BEGIN_INDEX}}", beginIndex.toString()),
                    true
            ).getJson(), new TypeReference<ApiMatchlistDto>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.info("Data not found, got exception" + e.getMessage());
            return matchLists;
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            return matchLists;
        } catch (Exception e) {
            this.logger.error("Got generic exception" + e.getMessage());
            return matchLists;
        }


        for (ApiMatchReferenceDTO apiMatchReferenceDto : apiMatchlistDto.getMatches()) {
            matchLists.add(this.matchFiller.fillMatchListGame(apiMatchReferenceDto, apiSummonerDTO));
        }

        // Iterar todas las partidas, cogiendo como primer resultado la siguiente partida a la última almacenada
        if (apiMatchlistDto.getEndIndex() < apiMatchlistDto.getTotalGames()) {
            this.matchListByAccount(apiSummonerDTO, region, apiMatchlistDto.getEndIndex() + 1);
        }
        return matchLists;
    }

    public MatchGameTimeline matchGameTimeline(MatchGame matchGame) {

        return null;
        //TODO: recuperar timeline GET /lol/match/v4/timelines/by-match/{matchId}
    }

    public MatchGame match(MatchGame matchGame) {
        ApiMatchDTO apiMatchDTO;

        try {
            apiMatchDTO = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.MATCHES.replace("{{GAME_ID}}", matchGame.getGameId().toString())
                            .replace("{{HOST}}", matchGame.getRegion().getHostName()),
                    true
            ).getJson(), new TypeReference<ApiMatchlistDto>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.error("El match no existe..." + matchGame.getGameId().toString());
            matchGame = this.matchGameRepository.findByGameId(matchGame.getGameId());
            if (matchGame != null) {
                matchGame.setRetrieved(true);
                matchGame.setRetrieving(false);
                matchGame.setExpired(true);
                this.matchGameRepository.save(matchGame);
            }
            return matchGame;
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            return matchGame;
        } catch (Exception e) {
            this.logger.error("Got generic exception" + e.getMessage());
            return matchGame;
        }

        this.matchFiller.fillMatchGame(apiMatchDTO);

        return matchGame;
    }
}
