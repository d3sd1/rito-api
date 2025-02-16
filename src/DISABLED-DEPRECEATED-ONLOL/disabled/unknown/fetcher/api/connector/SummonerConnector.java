package status.disabled.unknown.fetcher.api.connector;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import status.disabled.unknown.fetcher.api.ApiConnector;
import status.disabled.unknown.fetcher.api.endpoints.V4;
import status.disabled.unknown.fetcher.api.filler.SummonerFiller;
import status.disabled.unknown.fetcher.exceptions.ApiBadRequestException;
import status.disabled.unknown.fetcher.exceptions.ApiDownException;
import status.disabled.unknown.fetcher.exceptions.ApiUnauthorizedException;
import status.disabled.unknown.fetcher.exceptions.DataNotfoundException;
import status.disabled.unknown.fetcher.logger.LogService;
import status.disabled.unknown.model.*;
import status.disabled.unknown.repository.SummonerChampionMasteryRepository;
import status.disabled.unknown.repository.SummonerRepository;
import status.disabled.unknown.repository.SummonerTokenRepository;

import java.util.List;

@Service
public class SummonerConnector {

    @Autowired
    private MatchConnector matchConnector;

    @Autowired
    private SummonerChampionMasteryRepository summonerChampionMasteryRepository;

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private SummonerFiller summonerFiller;

    @Autowired
    private LogService logger;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private SummonerTokenRepository summonerTokenRepository;

    @Autowired
    private ObjectMapper jacksonMapper;

    public SummonerToken updateSummoner(Summoner summoner) {
        return this.updateSummoner(summoner, false);
    }

    public SummonerToken updateSummoner(Summoner summoner, boolean forceDelete) {
        SummonerToken summonerToken = null;
        try {
            ApiCall apiCall = this.apiConnector.get(
                    V4.SUMMONERS_BY_NAME
                            .replace("{{SUMMONER_NAME}}", summoner.getName().replaceAll(" ", ""))
                            .replace("{{HOST}}", summoner.getRegion().getHostName()),
                    true
            );
            summonerToken = this.jacksonMapper.reader(new InjectableValues.Std()
                    .addValue("apiKey", apiCall.getApiKey())
                    .addValue("summoner", summoner)).forType(SummonerToken.class).readValue(apiCall.getJson());
        } catch (DataNotfoundException e) {
            // summoner not found (due to region or name change). Check it by summonerId if it's on DB, else, delete.
            List<SummonerToken> summonerTokens = this.summonerTokenRepository.findBySummoner(summoner);
            if (summonerTokens.isEmpty() || forceDelete) {
                // disable summoner. Maybe if the future it turns back! Also cascade delete... Ejem lmao.
                summoner.setDisabled(true);
                this.logger.info("Disabled summoner " + summoner.getName());
                this.summonerRepository.save(summoner);
            } else {
                return this.updateSummoner(summonerTokens.get(0).getSummoner(), true);
            }
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            this.logger.error("ACTION REQUIRED. Malformed URL has thrown a 400 BAD REQUEST CODE. With exception " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null) {
                this.logger.error("Got generic exception" + e.getMessage());
            }
        }
        return summonerToken;
    }

    public List<SummonerChampionMastery> championMastery(SummonerToken summonerToken) {
        try {
            ApiCall apiCall = this.apiConnector.get(
                    V4.SUMMONER_CHAMPION_MASTERY
                            .replace("{{SUMMONER_ID}}", summonerToken.getSummonerTokenId())
                            .replace("{{HOST}}", summonerToken.getSummoner().getRegion().getHostName()),
                    true,
                    summonerToken.getApiKey()
            );
            this.jacksonMapper.reader(new InjectableValues.Std()
                    .addValue("apiKey", apiCall.getApiKey())
                    .addValue("summoner", summonerToken.getSummoner())).forType(SummonerChampionMastery.class).readValue(apiCall.getJson());
        } catch (DataNotfoundException e) {
            this.logger.info("Data not found, got exception");
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            this.logger.error("ACTION REQUIRED. Malformed URL has thrown a 400 BAD REQUEST CODE. With exception " + e.getMessage());
        } catch (Exception e) {
            if (e.getMessage() != null) {
                this.logger.error("Got generic exception" + e.getMessage());
            }
        }
        return this.summonerChampionMasteryRepository.findBySummoner(summonerToken.getSummoner());
    }

    public LiveGame inGame(SummonerToken summonerToken) {
        //TODO: optimizar esto... si ya esta en partida porque esta asociado a un livegame, devolverlo directamente en lugar de mandar request
        try {
            ApiCall apiCall = this.apiConnector.get(
                    V4.SUMMONER_ACTIVE_GAME
                            .replace("{{SUMMONER_ID}}", summonerToken.getSummonerTokenId())
                            .replace("{{HOST}}", summonerToken.getSummoner().getRegion().getHostName()),
                    true,
                    summonerToken.getApiKey()
            );
            this.logger.info("Summoner INGAME: " + summonerToken.getSummoner().getName());
            this.jacksonMapper.reader(new InjectableValues.Std()
                    .addValue("apiKey", apiCall.getApiKey())
                    .addValue("region", summonerToken.getSummoner().getRegion())
                    .addValue("summoner", summonerToken.getSummoner())).forType(LiveGame.class).readValue(apiCall.getJson());
        } catch (DataNotfoundException e) {
            // Check if he had previous games...
            //TODO: remove game from liveGames if it exists, searched by summoner.
            //TODO: also link liveGame with summoners and/or participants
            this.logger.info("Summoner is not in game: " + summonerToken.getSummoner().getName());
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            this.logger.error("ACTION REQUIRED. Malformed URL has thrown a 400 BAD REQUEST CODE. With exception " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null) {
                this.logger.error("Got generic exception" + e.getMessage());
            }
        }
        return null;
    }
}