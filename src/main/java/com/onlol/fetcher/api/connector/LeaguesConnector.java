package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.filler.SummonerFiller;
import com.onlol.fetcher.api.model.ApiLeagueListDTO;
import com.onlol.fetcher.exceptions.*;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

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
                    .addValue("summoner", summonerToken.getSummoner())
                    .addValue("region", null)).forType(SummonerLeague.class).readValue(apiCall.getJson());
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

    public League updateLeague(League league) {
        return this.updateLeague(league, false);
    }

    public League updateLeague(League league, boolean forceDelete) {
        try {
            //TODO: solve region nullpointer...
            if (league.getRegion() == null) {
                System.out.println("//TODO: solve nullpointer on leagues.");
                return null;
            }
            ApiCall apiCall = this.apiConnector.get(
                    V4.LEAGUES_BY_ID
                            .replace("{{HOST}}", league.getRegion().getHostName())
                            .replace("{{LEAGUE_ID}}", league.getRiotId()),
                    true
            );
            this.jacksonMapper.reader(new InjectableValues.Std()
                    .addValue("apiKey", apiCall.getApiKey())
                    .addValue("region", league.getRegion())).forType(League.class).readValue(apiCall.getJson());
        } catch (DataNotfoundException e) {
            // league not found
            league.setDisabled(true);
            this.logger.info("Disabled league " + league.getLeagueTier());
            this.leagueRepository.save(league);
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            this.logger.error("ACTION REQUIRED. Malformed URL has thrown a 400 BAD REQUEST CODE. With exception " + e.getMessage());
            e.printStackTrace();
        } catch (CannotCreateTransactionException e) {
            this.logger.info("Could not complete transaction due to shutdown on updateLeague.");
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null) {
                this.logger.error("Got generic exception" + e.getMessage());
            }
        }
        return this.leagueRepository.findByRiotId(league.getRiotId());
    }

    public List<SummonerLeague> findLeague(Region region, GameQueueType gameQueueType, LeagueRank leagueRank, LeagueTier leagueTier) {
        return this.findLeague(region, gameQueueType, leagueRank, leagueTier, 1);
    }

    public List<SummonerLeague> findLeague(Region region, GameQueueType gameQueueType, LeagueRank leagueRank, LeagueTier leagueTier, Integer pageNumber) {
        try {
            if (!leagueTier.isScrapeable()) { // CHALLENGER, MASTER AND GRANDMASTER ARE NOT AVAILABLE HERE, per example
                return new ArrayList<>();
            }
            ApiCall apiCall = this.apiConnector.get(
                    V4.LEAGUES_BY_QUEUE_TIER_DIVISION
                            .replace("{{HOST}}", region.getHostName())
                            .replace("{{PAGE_NUMBER}}", String.valueOf(pageNumber))
                            .replace("{{LEAGUE_QUEUE}}", gameQueueType.getKeyName())
                            .replace("{{LEAGUE_TIER}}", leagueTier.getKeyName())
                            .replace("{{LEAGUE_DIVISION}}", leagueRank.getKeyName()),
                    true
            );
            SummonerLeague summonerLeague = this.jacksonMapper.reader(new InjectableValues.Std()
                    .addValue("apiKey", apiCall.getApiKey())
                    .addValue("summoner", null)
                    .addValue("region", region)).forType(SummonerLeague.class).readValue(apiCall.getJson());
            if (summonerLeague == null) {
                throw new ApiPageNumberInvalidException();
            }
        } catch (ApiInvalidTierException e) {
            this.logger.info("Tier not scrapeable: " + leagueTier);
            leagueTier.setScrapeable(false);
            this.leagueTierRepository.save(leagueTier);
            return null;
        } catch (ApiPageNumberInvalidException e) {
            // No more pages.
            return null;
        } catch (DataNotfoundException | ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            // Don't show stacktrace... It's scary lol.
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null) {
                this.logger.error("Got generic exception " + e.getMessage());
            }
        }
        this.findLeague(region, gameQueueType, leagueRank, leagueTier, pageNumber + 1);
        return null;
    }

}