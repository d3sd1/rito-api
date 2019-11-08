package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.filler.SummonerFiller;
import com.onlol.fetcher.api.model.ApiLeagueListDTO;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaguesConnector {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private LeagueMiniSeriesRepository leagueMiniSeriesRepository;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private SummonerNameHistoricalRepository summonerNameHistoricalRepository;

    @Autowired
    private SummonerChampionMasteryRepository summonerChampionMasteryRepository;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private GameQueueRepository gameQueueRepository;

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private SummonerLeagueRepository summonerLeagueRepository;

    @Autowired
    private LeagueTierRepository leagueTierRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private LeagueRankRepository leagueRankRepository;

    @Autowired
    private GameQueueTypeRepository gameQueueTypeRepository;

    @Autowired
    private SummonerFiller summonerFiller;

    @Autowired
    private FeaturedGameIntervalRepository featuredGameIntervalRepository;

    @Autowired
    private LogService logger;

    @Autowired
    private ObjectMapper jacksonMapper;

    public List<SummonerLeague> summonerLeagues(SummonerToken summonerToken) {
        try {
            ApiCall apiCall = this.apiConnector.get(
                    V4.LEAGUES_BY_SUMMONER
                            .replace("{{SUMMONER_ID}}", summonerToken.getSummonerTokenId())
                            .replace("{{HOST}}", summonerToken.getSummoner().getRegion().getHostName()),
                    true,
                    summonerToken.getApiKey()
            );
            this.jacksonMapper.reader(new InjectableValues.Std()
                    .addValue("apiKey", apiCall.getApiKey())
                    .addValue("summoner", summonerToken.getSummoner())).forType(SummonerLeague.class).readValue(apiCall.getJson());
        } catch (DataNotfoundException e) {
            this.logger.warning("Summoner leagues not found: " + summonerToken.getSummoner().getName());
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error("No se ha podido retornar la liga del invocador " + summonerToken.getSummoner().getName());
        }
        return this.summonerLeagueRepository.findBySummoner(summonerToken.getSummoner());
    }

    public ArrayList<SummonerLeague> challengerLadderGlobal() {
        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        for (Region region : this.regionRepository.findAll()) {
            for (GameQueueType gameQueueType : this.gameQueueTypeRepository.findAll()) {
                summonerLeagues.addAll(this.challengerLadder(region, gameQueueType));
            }
        }
        return summonerLeagues;
    }

    public ArrayList<SummonerLeague> challengerLadder(Region region, GameQueueType gameQueueType) {
        ApiLeagueListDTO apiLeagueListDTO = null;
        try {
            ApiCall apiCall = this.apiConnector.get(
                    V4.LEAGUES_CHALLENGER
                            .replace("{{QUEUE}}", gameQueueType.getKeyName())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            );
            this.jacksonMapper.reader(new InjectableValues.Std()
                    .addValue("apiKey", apiCall.getApiKey())
                    .addValue("region", region)).forType(League.class).readValue(apiCall.getJson());
        } catch (DataNotfoundException e) {
            this.logger.warning("Summoner leagues for challenger ladder on region: " + region.getHostName());
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el listado de challengers " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public ArrayList<SummonerLeague> masterLadderGlobal() {
        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        for (Region region : this.regionRepository.findAll()) {
            for (GameQueueType gameQueueType : this.gameQueueTypeRepository.findAll()) {
                summonerLeagues.addAll(this.masterLadder(region, gameQueueType));
            }
        }
        return summonerLeagues;
    }

    public ArrayList<SummonerLeague> masterLadder(Region region, GameQueueType gameQueueType) {
        try {
            ApiCall apiCall = this.apiConnector.get(
                    V4.LEAGUES_MASTER
                            .replace("{{QUEUE}}", gameQueueType.getKeyName())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            );
            this.jacksonMapper.reader(new InjectableValues.Std()
                    .addValue("apiKey", apiCall.getApiKey())
                    .addValue("region", region)).forType(League.class).readValue(apiCall.getJson());
        } catch (DataNotfoundException e) {
            this.logger.warning("Summoner lagues for challenger ladder on region: " + region.getHostName());
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el listado de masters " + e.getMessage());
        }


        return new ArrayList<>();
    }

    public ArrayList<SummonerLeague> grandMasterLadderGlobal() {
        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        for (Region region : this.regionRepository.findAll()) {
            for (GameQueueType gameQueueType : this.gameQueueTypeRepository.findAll()) {
                summonerLeagues.addAll(this.grandMasterLadder(region, gameQueueType));
            }
        }
        return summonerLeagues;
    }

    public ArrayList<SummonerLeague> grandMasterLadder(Region region, GameQueueType gameQueueType) {
        try {
            ApiCall apiCall = this.apiConnector.get(
                    V4.LEAGUES_GRANDMASTER
                            .replace("{{QUEUE}}", gameQueueType.getKeyName())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            );
            this.jacksonMapper.reader(new InjectableValues.Std()
                    .addValue("apiKey", apiCall.getApiKey())
                    .addValue("region", region)).forType(League.class).readValue(apiCall.getJson());
        } catch (DataNotfoundException e) {
            this.logger.warning("Summoner lagues for challenger ladder on region: " + region.getHostName());
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el listado de masters " + e.getMessage());
        }

        return new ArrayList<>();
    }

    public List<FeaturedGameInterval> featuredGames() {
        ArrayList<FeaturedGameInterval> featuredGameIntervals = new ArrayList<>();
        for (Region region : this.regionRepository.findAll()) {
            featuredGameIntervals.add(this.featuredGames(region));
        }
        return featuredGameIntervals;
    }

    public FeaturedGameInterval featuredGames(Region region) {
        try {

            ApiCall apiCall = this.apiConnector.get(
                    V4.FEATURED_GAMES
                            .replace("{{HOST}}", region.getHostName()), true
            );
            this.jacksonMapper.reader(new InjectableValues.Std()
                    .addValue("apiKey", apiCall.getApiKey())
                    .addValue("region", region)).forType(FeaturedGameInterval.class).readValue(apiCall.getJson());
        } catch (DataNotfoundException e) {
            // Not catchable since PBE has no featured games lmao :)
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el listado de masters " + e.getMessage());
        }
        return null;
    }
}
// TODO: guardar top peak (liga actual) usuario y fecha en cada recarga para hacer graficas