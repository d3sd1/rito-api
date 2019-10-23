package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LeaguesConnector {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private MatchGameRepository matchGameRepository;

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
    private LeagueMiniSeriesRepository leagueMiniSeriesRepository;

    @Autowired
    private FeaturedGameIntervalRepository featuredGameIntervalRepository;

    @Autowired
    private LogService logger;

    @Autowired
    private ObjectMapper jacksonMapper;

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
        } catch (IOException e) {
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


            /* Check if player is playing miniseries */
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
        return summonerLeagues;
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
            String json = this.apiConnector.get(
                    V4.LEAGUES_CHALLENGER
                            .replace("{{QUEUE}}", gameQueueType.getKeyName())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            );
            if (json != null) {
                apiLeagueListDTO = this.jacksonMapper.readValue(json, new TypeReference<ApiLeagueListDTO>() {
                });
            }
        } catch (DataNotfoundException e) {
            this.logger.warning("Summoner lagues for challenger ladder on region: " + region.getHostName());
        } catch (IOException e) {
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
            Optional<Summoner> opSummoner = this.summonerRepository.findById(apiLeagueItemDTO.getSummonerId());
            Summoner summoner = null;
            if (!opSummoner.isPresent()) {
                summoner = new Summoner();
                summoner.setId(apiLeagueItemDTO.getSummonerId());
                summoner.setName(apiLeagueItemDTO.getSummonerName());
                summoner.setRegion(region);
                // Set prev date, so we queue this summoner for being updated.
                summoner.setLastTimeUpdated(LocalDateTime.of(2010, 9, 9, 0, 0));
                this.summonerRepository.save(summoner);
            }

            SummonerLeague summonerLeague = this.summonerLeagueRepository.findBySummonerAndQueueType(summoner, gameQueueType);
            if (summonerLeague == null) {
                summonerLeague = new SummonerLeague();
                summonerLeague.setSummoner(summoner);
                summonerLeague.setGameQueueType(gameQueueType);
                this.summonerLeagueRepository.save(summonerLeague);
            }
            summonerLeague.setHotStreak(apiLeagueItemDTO.isHotStreak());
            summonerLeague.setWins(apiLeagueItemDTO.getWins());
            summonerLeague.setVeteran(apiLeagueItemDTO.isVeteran());
            summonerLeague.setLosses(apiLeagueItemDTO.getLosses());
            summonerLeague.setInactive(apiLeagueItemDTO.isInactive());
            summonerLeague.setFreshBlood(apiLeagueItemDTO.isFreshBlood());
            summonerLeague.setLeaguePoints(apiLeagueItemDTO.getLeaguePoints());
            summonerLeague.setLeagueTier(leagueTier);
            LeagueRank leagueRank = this.leagueRankRepository.findByKeyName(apiLeagueItemDTO.getRank());
            if (leagueRank == null) {
                leagueRank = new LeagueRank();
                leagueRank.setKeyName(apiLeagueItemDTO.getRank());
                this.leagueRankRepository.save(leagueRank);
            }
            summonerLeague.setLeagueRank(leagueRank);
            this.summonerLeagueRepository.save(summonerLeague);
        }
        return summonerLeagues;
    }

    // Master


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
        try {
            apiLeagueListDTO = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.LEAGUES_MASTER
                            .replace("{{QUEUE}}", gameQueueType.getKeyName())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            ), new TypeReference<ApiLeagueListDTO>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.warning("Summoner lagues for challenger ladder on region: " + region.getHostName());
        } catch (IOException e) {
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
            Optional<Summoner> opSummoner = this.summonerRepository.findById(apiLeagueItemDTO.getSummonerId());
            Summoner summoner = null;
            if (!opSummoner.isPresent()) {
                summoner = new Summoner();
                summoner.setId(apiLeagueItemDTO.getSummonerId());
                summoner.setName(apiLeagueItemDTO.getSummonerName());
                summoner.setRegion(region);
                // Set prev date, so we queue this summoner for being updated.
                summoner.setLastTimeUpdated(LocalDateTime.of(2010, 9, 9, 0, 0));
                this.summonerRepository.save(summoner);
            }

            SummonerLeague summonerLeague = this.summonerLeagueRepository.findBySummonerAndQueueType(summoner, gameQueueType);
            if (summonerLeague == null) {
                summonerLeague = new SummonerLeague();
                summonerLeague.setSummoner(summoner);
                summonerLeague.setGameQueueType(gameQueueType);
                this.summonerLeagueRepository.save(summonerLeague);
            }
            summonerLeague.setHotStreak(apiLeagueItemDTO.isHotStreak());
            summonerLeague.setWins(apiLeagueItemDTO.getWins());
            summonerLeague.setVeteran(apiLeagueItemDTO.isVeteran());
            summonerLeague.setLosses(apiLeagueItemDTO.getLosses());
            summonerLeague.setInactive(apiLeagueItemDTO.isInactive());
            summonerLeague.setFreshBlood(apiLeagueItemDTO.isFreshBlood());
            summonerLeague.setLeaguePoints(apiLeagueItemDTO.getLeaguePoints());
            summonerLeague.setLeagueTier(leagueTier);
            LeagueRank leagueRank = this.leagueRankRepository.findByKeyName(apiLeagueItemDTO.getRank());
            if (leagueRank == null) {
                leagueRank = new LeagueRank();
                leagueRank.setKeyName(apiLeagueItemDTO.getRank());
                this.leagueRankRepository.save(leagueRank);
            }
            summonerLeague.setLeagueRank(leagueRank);
            this.summonerLeagueRepository.save(summonerLeague);
        }
        return summonerLeagues;
    }


    // GrandMaster


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
        try {
            String json = this.apiConnector.get(
                    V4.LEAGUES_GRANDMASTER
                            .replace("{{QUEUE}}", gameQueueType.getKeyName())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            );
            if (json != null) {
                apiLeagueListDTO = this.jacksonMapper.readValue(json, new TypeReference<ApiLeagueListDTO>() {
                });
            }
        } catch (DataNotfoundException e) {
            this.logger.warning("Summoner lagues for challenger ladder on region: " + region.getHostName());
        } catch (IOException e) {
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
            Optional<Summoner> opSummoner = this.summonerRepository.findById(apiLeagueItemDTO.getSummonerId());
            Summoner summoner = null;
            if (!opSummoner.isPresent()) {
                summoner = new Summoner();
                summoner.setId(apiLeagueItemDTO.getSummonerId());
                summoner.setName(apiLeagueItemDTO.getSummonerName());
                summoner.setRegion(region);
                // Set prev date, so we queue this summoner for being updated.
                summoner.setLastTimeUpdated(LocalDateTime.of(2010, 9, 9, 0, 0));
                this.summonerRepository.save(summoner);
            }

            SummonerLeague summonerLeague = this.summonerLeagueRepository.findBySummonerAndQueueType(summoner, gameQueueType);
            if (summonerLeague == null) {
                summonerLeague = new SummonerLeague();
                summonerLeague.setSummoner(summoner);
                summonerLeague.setGameQueueType(gameQueueType);
                this.summonerLeagueRepository.save(summonerLeague);
            }
            summonerLeague.setHotStreak(apiLeagueItemDTO.isHotStreak());
            summonerLeague.setWins(apiLeagueItemDTO.getWins());
            summonerLeague.setVeteran(apiLeagueItemDTO.isVeteran());
            summonerLeague.setLosses(apiLeagueItemDTO.getLosses());
            summonerLeague.setInactive(apiLeagueItemDTO.isInactive());
            summonerLeague.setFreshBlood(apiLeagueItemDTO.isFreshBlood());
            summonerLeague.setLeaguePoints(apiLeagueItemDTO.getLeaguePoints());
            summonerLeague.setLeagueTier(leagueTier);
            LeagueRank leagueRank = this.leagueRankRepository.findByKeyName(apiLeagueItemDTO.getRank());
            if (leagueRank == null) {
                leagueRank = new LeagueRank();
                leagueRank.setKeyName(apiLeagueItemDTO.getRank());
                this.leagueRankRepository.save(leagueRank);
            }
            summonerLeague.setLeagueRank(leagueRank);
            this.summonerLeagueRepository.save(summonerLeague);
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
        ApiFeaturedGamesDTO apiFeaturedGamesDTO = null;
        try {
            String json = this.apiConnector.get(
                    V4.FEATURED_GAMES
                            .replace("{{HOST}}", region.getHostName()), true);
            if (json != null) {
                apiFeaturedGamesDTO = this.jacksonMapper.readValue(json, new TypeReference<ApiFeaturedGamesDTO>() {
                });
            } else {
                return new FeaturedGameInterval();
            }
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
/*
            sampleFeaturedGameInfo.getGameLength()
            sampleFeaturedGameInfo.getGameMode()
            sampleFeaturedGameInfo.getGameQueueConfigId()
            sampleFeaturedGameInfo.getGameStartTime()
            sampleFeaturedGameInfo.getGameType()
            sampleFeaturedGameInfo.getMapId()
            sampleFeaturedGameInfo.getObservers()
            sampleFeaturedGameInfo.getParticipants();*/


            this.matchGameRepository.save(matchGame);
            matchGames.add(matchGame);
        }
        featuredGameInterval.setMatchGames(matchGames);
        featuredGameInterval.setRegion(region);
        featuredGameInterval.setTimestamp(LocalDateTime.now());
        this.featuredGameIntervalRepository.save(featuredGameInterval);

        return featuredGameInterval;
    }
}
// TODO: guardar top peak (liga actual) usuario y fecha en cada recarga para hacer graficas