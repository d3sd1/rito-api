package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.filler.SummonerFiller;
import com.onlol.fetcher.api.filler.SummonerLeagueFiller;
import com.onlol.fetcher.api.model.ApiLeagueItemDTO;
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
    private SummonerLeagueFiller summonerLeagueFiller;

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
/* TODO
    public ArrayList<SummonerLeague> summonerLeagues(Summoner summoner) {
        ArrayList<ApiLeagueItemDTO> apiLeagueItemDTOS = null;

        try {
            apiLeagueItemDTOS = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.LEAGUES_BY_SUMMONER
                            .replace("{{SUMMONER_ID}}", summoner.getId())
                            .replace("{{HOST}}", summoner.getRegion().getHostName()),
                    true
            ), new TypeReference<ArrayList<ApiLeagueItemDTO>>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.warning("Summoner lagues not found: " + summoner.getName());
        } catch (Exception e) {
            this.logger.error("No se ha podido retornar la liga del invocador " + summoner.getName());
        }

        if (apiLeagueItemDTOS == null) {
            return new ArrayList<>();
        }
        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        for (ApiLeagueItemDTO apiLeagueItemDTO : apiLeagueItemDTOS) {
            GameQueueType queuetype = this.gameQueueTypeRepository.findByKeyName(apiLeagueItemDTO.getQueueType());
            if (queuetype == null) {
                queuetype = new GameQueueType();
                queuetype.setKeyName(apiLeagueItemDTO.getQueueType());
                this.gameQueueTypeRepository.save(queuetype);
            }
            SummonerLeague summonerLeague = this.summonerLeagueRepository.findBySummonerAndQueueType(summoner, queuetype);
            if (summonerLeague == null) {
                summonerLeague = new SummonerLeague();
                summonerLeague.setSummoner(summoner);
                summonerLeague.setGameQueueType(queuetype);
                this.summonerLeagueRepository.save(summonerLeague);
            }
            LeagueTier leagueTier = this.leagueTierRepository.findByKeyName(apiLeagueItemDTO.getTier());
            if (leagueTier == null) {
                leagueTier = new LeagueTier();
                leagueTier.setKeyName(apiLeagueItemDTO.getTier());
                this.leagueTierRepository.save(leagueTier);
            }
            summonerLeague.setLeagueTier(leagueTier);


            LeagueRank leagueRank = this.leagueRankRepository.findByKeyName(apiLeagueItemDTO.getRank());
            if (leagueRank == null) {
                leagueRank = new LeagueRank();
                leagueRank.setKeyName(apiLeagueItemDTO.getRank());
                this.leagueRankRepository.save(leagueRank);
            }
            summonerLeague.setLeagueRank(leagueRank);


            /* Check if player is playing miniseries *
            LeagueMiniSeries leagueMiniSeries = null;
            if (apiLeagueItemDTO.getMiniSeries() != null) {
                leagueMiniSeries = this.leagueMiniSeriesRepository.findBySummoner(summoner);
                if (leagueMiniSeries == null) {
                    leagueMiniSeries = new LeagueMiniSeries();
                    leagueMiniSeries.setSummoner(summoner);
                    this.leagueMiniSeriesRepository.save(leagueMiniSeries);
                }

                leagueMiniSeries.setWins(apiLeagueItemDTO.getMiniSeries().getWins());
                leagueMiniSeries.setLosses(apiLeagueItemDTO.getMiniSeries().getLosses());
                leagueMiniSeries.setProgress(apiLeagueItemDTO.getMiniSeries().getProgress());
                leagueMiniSeries.setTarget(apiLeagueItemDTO.getMiniSeries().getTarget());
                this.leagueMiniSeriesRepository.save(leagueMiniSeries);
            }

            summonerLeague.setLeagueMiniSeries(leagueMiniSeries);

            summonerLeague.setHotStreak(apiLeagueItemDTO.isHotStreak());
            summonerLeague.setWins(apiLeagueItemDTO.getWins());
            summonerLeague.setLosses(apiLeagueItemDTO.getLosses());
            summonerLeague.setVeteran(apiLeagueItemDTO.isVeteran());
            summonerLeague.setInactive(apiLeagueItemDTO.isInactive());
            summonerLeague.setFreshBlood(apiLeagueItemDTO.isFreshBlood());
            summonerLeague.setInactive(apiLeagueItemDTO.isInactive());
            this.summonerLeagueRepository.save(summonerLeague);
        }
        return new ArrayList<>();
    }*/

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
        ApiCall apiCall = null;
        try {
            apiCall = this.apiConnector.get(
                    V4.LEAGUES_CHALLENGER
                            .replace("{{QUEUE}}", gameQueueType.getKeyName())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            );
            apiLeagueListDTO = this.jacksonMapper.readValue(apiCall.getJson(), new TypeReference<ApiLeagueListDTO>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.warning("Summoner lagues for challenger ladder on region: " + region.getHostName());
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el listado de challengers " + e.getMessage());
        }


        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        if (apiLeagueListDTO == null) {
            return summonerLeagues;
        }

        /* Find and save league tier */
        LeagueTier leagueTier = this.leagueTierRepository.findByKeyName(apiLeagueListDTO.getTier());
        if (leagueTier == null && apiLeagueListDTO.getTier() != null) {
            leagueTier = new LeagueTier();
            leagueTier.setKeyName(apiLeagueListDTO.getTier());
            this.leagueTierRepository.save(leagueTier);
        }

        /* Find and save league */
        League league = this.leagueRepository.findByRiotId(apiLeagueListDTO.getLeagueId());
        if (league == null && apiLeagueListDTO.getLeagueId() != null) {
            league = new League();
            league.setRiotId(apiLeagueListDTO.getLeagueId());
            this.leagueRepository.save(league);
        }

        for (ApiLeagueItemDTO apiLeagueItemDTO : apiLeagueListDTO.getEntries()) {
            Summoner summoner = this.summonerFiller.fillSummoner(apiLeagueItemDTO, region, apiCall.getApiKey());
            summonerLeagues.add(this.summonerLeagueFiller.fillSummonerLeague(summoner, gameQueueType, leagueTier, apiLeagueItemDTO));
        }
        return summonerLeagues;
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
        ApiLeagueListDTO apiLeagueListDTO = null;
        ApiCall apiCall = null;
        try {
            apiCall = this.apiConnector.get(
                    V4.LEAGUES_MASTER
                            .replace("{{QUEUE}}", gameQueueType.getKeyName())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            );
            apiLeagueListDTO = this.jacksonMapper.readValue(apiCall.getJson(), new TypeReference<ApiLeagueListDTO>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.warning("Summoner lagues for challenger ladder on region: " + region.getHostName());
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el listado de masters " + e.getMessage());
        }


        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        if (apiLeagueListDTO == null) {
            return summonerLeagues;
        }

        /* Find and save league tier */
        LeagueTier leagueTier = this.leagueTierRepository.findByKeyName(apiLeagueListDTO.getTier());
        if (leagueTier == null && apiLeagueListDTO.getTier() != null) {
            leagueTier = new LeagueTier();
            leagueTier.setKeyName(apiLeagueListDTO.getTier());
            this.leagueTierRepository.save(leagueTier);
        }


        /* Find and save league */
        League league = this.leagueRepository.findByRiotId(apiLeagueListDTO.getLeagueId());
        if (league == null && apiLeagueListDTO.getLeagueId() != null) {
            league = new League();
            league.setRiotId(apiLeagueListDTO.getLeagueId());
            this.leagueRepository.save(league);
        }

        for (ApiLeagueItemDTO apiLeagueItemDTO : apiLeagueListDTO.getEntries()) {
            Summoner summoner = this.summonerFiller.fillSummoner(apiLeagueItemDTO, region, apiCall.getApiKey());
            summonerLeagues.add(this.summonerLeagueFiller.fillSummonerLeague(summoner, gameQueueType, leagueTier, apiLeagueItemDTO));
        }
        return summonerLeagues;
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
        ApiLeagueListDTO apiLeagueListDTO = null;
        ApiCall apiCall = null;
        try {
            apiCall = this.apiConnector.get(
                    V4.LEAGUES_GRANDMASTER
                            .replace("{{QUEUE}}", gameQueueType.getKeyName())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            );
            apiLeagueListDTO = this.jacksonMapper.readValue(apiCall.getJson(), new TypeReference<ApiLeagueListDTO>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.warning("Summoner lagues for challenger ladder on region: " + region.getHostName());
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el listado de masters " + e.getMessage());
        }


        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        if (apiLeagueListDTO == null) {
            return summonerLeagues;
        }

        /* Find and save league tier */
        LeagueTier leagueTier = this.leagueTierRepository.findByKeyName(apiLeagueListDTO.getTier());
        if (leagueTier == null && apiLeagueListDTO.getTier() != null) {
            leagueTier = new LeagueTier();
            leagueTier.setKeyName(apiLeagueListDTO.getTier());
            this.leagueTierRepository.save(leagueTier);
        }

        /* Find and save league */
        League league = this.leagueRepository.findByRiotId(apiLeagueListDTO.getLeagueId());
        if (league == null && apiLeagueListDTO.getLeagueId() != null) {
            league = new League();
            league.setRiotId(apiLeagueListDTO.getLeagueId());
            this.leagueRepository.save(league);
        }

        for (ApiLeagueItemDTO apiLeagueItemDTO : apiLeagueListDTO.getEntries()) {
            Summoner summoner = this.summonerFiller.fillSummoner(apiLeagueItemDTO, region, apiCall.getApiKey());
            summonerLeagues.add(this.summonerLeagueFiller.fillSummonerLeague(summoner, gameQueueType, leagueTier, apiLeagueItemDTO));

        }
        return summonerLeagues;
    }

    public List<FeaturedGameInterval> featuredGames() {
        ArrayList<FeaturedGameInterval> featuredGameIntervals = new ArrayList<>();
        for (Region region : this.regionRepository.findAll()) {
            featuredGameIntervals.add(this.featuredGames(region));
        }
        return featuredGameIntervals;
    }

    public FeaturedGameInterval featuredGames(Region region) {
       /* TODO  ApiFeaturedGamesDTO apiFeaturedGamesDTO = null;
        try {
            apiFeaturedGamesDTO = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.FEATURED_GAMES
                            .replace("{{HOST}}", region.getHostName()), true).getJson(), new TypeReference<ApiFeaturedGamesDTO>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error("No se ha podido retornar las partidas promocionadas " + e.getMessage());
        }
        FeaturedGameInterval featuredGameInterval = new FeaturedGameInterval();
        featuredGameInterval.setClientRefreshInterval(apiFeaturedGamesDTO.getClientRefreshInterval());

        ArrayList<MatchGame> matchGames = new ArrayList<>();
        for (ApiFeaturedGameInfoDTO apiFeaturedGameInfoDto : apiFeaturedGamesDTO.getGameList()) {
            System.out.println(apiFeaturedGameInfoDto.getGameId());
            MatchGame matchGame = this.matchGameRepository.findByGameId(apiFeaturedGameInfoDto.getGameId());
            if (matchGame == null) {
                matchGame = new MatchGame();
                matchGame.setGameId(apiFeaturedGameInfoDto.getGameId());
                matchGame.setRegion(region);
                this.matchGameRepository.save(matchGame);
            }
            //TODO: completar esta clase
            for (ApiBannedChampionDTO apiBannedChampionDTO : apiFeaturedGameInfoDto.getBannedChampions()) {
                continue;
            }

            sampleFeaturedGameInfo.getGameLength()
            sampleFeaturedGameInfo.getGameMode()
            sampleFeaturedGameInfo.getGameQueueConfigId()
            sampleFeaturedGameInfo.getGameStartTime()
            sampleFeaturedGameInfo.getGameType()
            sampleFeaturedGameInfo.getMapId()
            sampleFeaturedGameInfo.getObservers()
            sampleFeaturedGameInfo.getParticipants();


            this.matchGameRepository.save(matchGame);
            matchGames.add(matchGame);
        }
        featuredGameInterval.setMatchGames(matchGames);
        featuredGameInterval.setRegion(region);
        featuredGameInterval.setTimestamp(LocalDateTime.now());
        this.featuredGameIntervalRepository.save(featuredGameInterval);

        return featuredGameInterval;*/
        return null;
    }
}
// TODO: guardar top peak (liga actual) usuario y fecha en cada recarga para hacer graficas