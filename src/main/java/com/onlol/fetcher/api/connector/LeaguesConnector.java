package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.api.repository.*;
import com.onlol.fetcher.api.riotModel.*;
import com.onlol.fetcher.logger.LogService;
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
    private QueueRepository queueRepository;

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
    private QueueTypeRepository queueTypeRepository;

    @Autowired
    private LeagueMiniSeriesRepository leagueMiniSeriesRepository;

    @Autowired
    private FeaturedGameIntervalRepository featuredGameIntervalRepository;

    @Autowired
    private LogService logger;

    @Autowired
    private ObjectMapper jacksonMapper;

    public ArrayList<SummonerLeague> summonerLeagues(Summoner summoner) {
        ArrayList<SampleSummonerLeague> sampleSummonerLeagues = null;
        try {
            sampleSummonerLeagues = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.LEAGUES_BY_SUMMONER
                            .replace("{{SUMMONER_ID}}", summoner.getId())
                            .replace("{{HOST}}", summoner.getRegion().getHostName()),
                    true
            ), new TypeReference<ArrayList<SampleSummonerLeague>>() {
            });
        } catch (IOException e) {
            this.logger.error("No se ha podido retornar la liga del invocador " + summoner.getName());
        }

        if (sampleSummonerLeagues == null) {
            return new ArrayList<>();
        }
        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        for (SampleSummonerLeague sampleSummonerLeague : sampleSummonerLeagues) {
            QueueType queuetype = this.queueTypeRepository.findByKeyName(sampleSummonerLeague.getQueueType());
            if (queuetype == null) {
                queuetype = new QueueType();
                queuetype.setKeyName(sampleSummonerLeague.getQueueType());
                this.queueTypeRepository.save(queuetype);
            }
            SummonerLeague summonerLeague = this.summonerLeagueRepository.findBySummonerAndQueueType(summoner, queuetype);
            if (summonerLeague == null) {
                summonerLeague = new SummonerLeague();
                summonerLeague.setSummoner(summoner);
                summonerLeague.setQueueType(queuetype);
                this.summonerLeagueRepository.save(summonerLeague);
            }
            LeagueTier leagueTier = this.leagueTierRepository.findByKeyName(sampleSummonerLeague.getTier());
            if (leagueTier == null) {
                leagueTier = new LeagueTier();
                leagueTier.setKeyName(sampleSummonerLeague.getTier());
                this.leagueTierRepository.save(leagueTier);
            }
            summonerLeague.setLeagueTier(leagueTier);


            LeagueRank leagueRank = this.leagueRankRepository.findByKeyName(sampleSummonerLeague.getRank());
            if (leagueRank == null) {
                leagueRank = new LeagueRank();
                leagueRank.setKeyName(sampleSummonerLeague.getRank());
                this.leagueRankRepository.save(leagueRank);
            }
            summonerLeague.setLeagueRank(leagueRank);


            /* Check if player is playing miniseries */
            LeagueMiniSeries leagueMiniSeries = null;
            if (sampleSummonerLeague.getMiniSeries() != null) {
                leagueMiniSeries = this.leagueMiniSeriesRepository.findBySummoner(summoner);
                if (leagueMiniSeries == null) {
                    leagueMiniSeries = new LeagueMiniSeries();
                    leagueMiniSeries.setSummoner(summoner);
                    this.leagueMiniSeriesRepository.save(leagueMiniSeries);
                }

                leagueMiniSeries.setWins(sampleSummonerLeague.getMiniSeries().getWins());
                leagueMiniSeries.setLosses(sampleSummonerLeague.getMiniSeries().getLosses());
                leagueMiniSeries.setProgress(sampleSummonerLeague.getMiniSeries().getProgress());
                leagueMiniSeries.setTarget(sampleSummonerLeague.getMiniSeries().getTarget());
                this.leagueMiniSeriesRepository.save(leagueMiniSeries);
            }

            summonerLeague.setLeagueMiniSeries(leagueMiniSeries);

            summonerLeague.setHotStreak(sampleSummonerLeague.isHotStreak());
            summonerLeague.setWins(sampleSummonerLeague.getWins());
            summonerLeague.setLosses(sampleSummonerLeague.getLosses());
            summonerLeague.setVeteran(sampleSummonerLeague.isVeteran());
            summonerLeague.setInactive(sampleSummonerLeague.isInactive());
            summonerLeague.setFreshBlood(sampleSummonerLeague.isFreshBlood());
            summonerLeague.setInactive(sampleSummonerLeague.isInactive());
            this.summonerLeagueRepository.save(summonerLeague);
        }
        return summonerLeagues;
    }

    public ArrayList<SummonerLeague> challengerLadderGlobal() {
        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        for (Region region : this.regionRepository.findAll()) {
            for (QueueType queueType : this.queueTypeRepository.findAll()) {
                summonerLeagues.addAll(this.challengerLadder(region, queueType));
            }
        }
        return summonerLeagues;
    }

    public ArrayList<SummonerLeague> challengerLadder(Region region, QueueType queueType) {
        SampleSummonerLeagueList sampleSummonerLeagueList = null;
        try {
            sampleSummonerLeagueList = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.LEAGUES_CHALLENGER
                            .replace("{{QUEUE}}", queueType.getKeyName())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            ), new TypeReference<SampleSummonerLeagueList>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el listado de challengers " + e.getMessage());
        }


        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        if (sampleSummonerLeagueList == null) {
            return summonerLeagues;
        }

        /* Find and save league tier */
        LeagueTier leagueTier = this.leagueTierRepository.findByKeyName(sampleSummonerLeagueList.getTier());
        if (leagueTier == null && sampleSummonerLeagueList.getTier() != null) {
            leagueTier = new LeagueTier();
            leagueTier.setKeyName(sampleSummonerLeagueList.getTier());
            this.leagueTierRepository.save(leagueTier);
        }


        /* Find and save league */
        League league = this.leagueRepository.findByRiotId(sampleSummonerLeagueList.getLeagueId());
        if (league == null && sampleSummonerLeagueList.getLeagueId() != null) {
            league = new League();
            league.setRiotId(sampleSummonerLeagueList.getLeagueId());
            this.leagueRepository.save(league);
        }

        for (SampleSummonerLeague sampleSummonerLeague : sampleSummonerLeagueList.getEntries()) {
            Optional<Summoner> opSummoner = this.summonerRepository.findById(sampleSummonerLeague.getSummonerId());
            Summoner summoner = null;
            if (!opSummoner.isPresent()) {
                summoner = new Summoner();
                summoner.setId(sampleSummonerLeague.getSummonerId());
                summoner.setName(sampleSummonerLeague.getSummonerName());
                summoner.setRegion(region);
                // Set prev date, so we queue this summoner for being updated.
                summoner.setLastTimeUpdated(LocalDateTime.of(2010, 9, 9, 0, 0));
                this.summonerRepository.save(summoner);
            }

            SummonerLeague summonerLeague = this.summonerLeagueRepository.findBySummonerAndQueueType(summoner, queueType);
            if (summonerLeague == null) {
                summonerLeague = new SummonerLeague();
                summonerLeague.setSummoner(summoner);
                summonerLeague.setQueueType(queueType);
                this.summonerLeagueRepository.save(summonerLeague);
            }
            summonerLeague.setHotStreak(sampleSummonerLeague.isHotStreak());
            summonerLeague.setWins(sampleSummonerLeague.getWins());
            summonerLeague.setVeteran(sampleSummonerLeague.isVeteran());
            summonerLeague.setLosses(sampleSummonerLeague.getLosses());
            summonerLeague.setInactive(sampleSummonerLeague.isInactive());
            summonerLeague.setFreshBlood(sampleSummonerLeague.isFreshBlood());
            summonerLeague.setLeaguePoints(sampleSummonerLeague.getLeaguePoints());
            summonerLeague.setLeagueTier(leagueTier);
            LeagueRank leagueRank = this.leagueRankRepository.findByKeyName(sampleSummonerLeague.getRank());
            if (leagueRank == null) {
                leagueRank = new LeagueRank();
                leagueRank.setKeyName(sampleSummonerLeague.getRank());
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
            for (QueueType queueType : this.queueTypeRepository.findAll()) {
                summonerLeagues.addAll(this.masterLadder(region, queueType));
            }
        }
        return summonerLeagues;
    }

    public ArrayList<SummonerLeague> masterLadder(Region region, QueueType queueType) {
        SampleSummonerLeagueList sampleSummonerLeagueList = null;
        try {
            sampleSummonerLeagueList = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.LEAGUES_MASTER
                            .replace("{{QUEUE}}", queueType.getKeyName())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            ), new TypeReference<SampleSummonerLeagueList>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el listado de masters " + e.getMessage());
        }


        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        if (sampleSummonerLeagueList == null) {
            return summonerLeagues;
        }

        /* Find and save league tier */
        LeagueTier leagueTier = this.leagueTierRepository.findByKeyName(sampleSummonerLeagueList.getTier());
        if (leagueTier == null && sampleSummonerLeagueList.getTier() != null) {
            leagueTier = new LeagueTier();
            leagueTier.setKeyName(sampleSummonerLeagueList.getTier());
            this.leagueTierRepository.save(leagueTier);
        }


        /* Find and save league */
        League league = this.leagueRepository.findByRiotId(sampleSummonerLeagueList.getLeagueId());
        if (league == null && sampleSummonerLeagueList.getLeagueId() != null) {
            league = new League();
            league.setRiotId(sampleSummonerLeagueList.getLeagueId());
            this.leagueRepository.save(league);
        }

        for (SampleSummonerLeague sampleSummonerLeague : sampleSummonerLeagueList.getEntries()) {
            Optional<Summoner> opSummoner = this.summonerRepository.findById(sampleSummonerLeague.getSummonerId());
            Summoner summoner = null;
            if (!opSummoner.isPresent()) {
                summoner = new Summoner();
                summoner.setId(sampleSummonerLeague.getSummonerId());
                summoner.setName(sampleSummonerLeague.getSummonerName());
                summoner.setRegion(region);
                // Set prev date, so we queue this summoner for being updated.
                summoner.setLastTimeUpdated(LocalDateTime.of(2010, 9, 9, 0, 0));
                this.summonerRepository.save(summoner);
            }

            SummonerLeague summonerLeague = this.summonerLeagueRepository.findBySummonerAndQueueType(summoner, queueType);
            if (summonerLeague == null) {
                summonerLeague = new SummonerLeague();
                summonerLeague.setSummoner(summoner);
                summonerLeague.setQueueType(queueType);
                this.summonerLeagueRepository.save(summonerLeague);
            }
            summonerLeague.setHotStreak(sampleSummonerLeague.isHotStreak());
            summonerLeague.setWins(sampleSummonerLeague.getWins());
            summonerLeague.setVeteran(sampleSummonerLeague.isVeteran());
            summonerLeague.setLosses(sampleSummonerLeague.getLosses());
            summonerLeague.setInactive(sampleSummonerLeague.isInactive());
            summonerLeague.setFreshBlood(sampleSummonerLeague.isFreshBlood());
            summonerLeague.setLeaguePoints(sampleSummonerLeague.getLeaguePoints());
            summonerLeague.setLeagueTier(leagueTier);
            LeagueRank leagueRank = this.leagueRankRepository.findByKeyName(sampleSummonerLeague.getRank());
            if (leagueRank == null) {
                leagueRank = new LeagueRank();
                leagueRank.setKeyName(sampleSummonerLeague.getRank());
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
            for (QueueType queueType : this.queueTypeRepository.findAll()) {
                summonerLeagues.addAll(this.grandMasterLadder(region, queueType));
            }
        }
        return summonerLeagues;
    }

    public ArrayList<SummonerLeague> grandMasterLadder(Region region, QueueType queueType) {
        SampleSummonerLeagueList sampleSummonerLeagueList = null;
        try {
            sampleSummonerLeagueList = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.LEAGUES_GRANDMASTER
                            .replace("{{QUEUE}}", queueType.getKeyName())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            ), new TypeReference<SampleSummonerLeagueList>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            this.logger.error("No se ha podido retornar el listado de masters " + e.getMessage());
        }


        ArrayList<SummonerLeague> summonerLeagues = new ArrayList<>();
        if (sampleSummonerLeagueList == null) {
            return summonerLeagues;
        }

        /* Find and save league tier */
        LeagueTier leagueTier = this.leagueTierRepository.findByKeyName(sampleSummonerLeagueList.getTier());
        if (leagueTier == null && sampleSummonerLeagueList.getTier() != null) {
            leagueTier = new LeagueTier();
            leagueTier.setKeyName(sampleSummonerLeagueList.getTier());
            this.leagueTierRepository.save(leagueTier);
        }


        /* Find and save league */
        League league = this.leagueRepository.findByRiotId(sampleSummonerLeagueList.getLeagueId());
        if (league == null && sampleSummonerLeagueList.getLeagueId() != null) {
            league = new League();
            league.setRiotId(sampleSummonerLeagueList.getLeagueId());
            this.leagueRepository.save(league);
        }

        for (SampleSummonerLeague sampleSummonerLeague : sampleSummonerLeagueList.getEntries()) {
            Optional<Summoner> opSummoner = this.summonerRepository.findById(sampleSummonerLeague.getSummonerId());
            Summoner summoner = null;
            if (!opSummoner.isPresent()) {
                summoner = new Summoner();
                summoner.setId(sampleSummonerLeague.getSummonerId());
                summoner.setName(sampleSummonerLeague.getSummonerName());
                summoner.setRegion(region);
                // Set prev date, so we queue this summoner for being updated.
                summoner.setLastTimeUpdated(LocalDateTime.of(2010, 9, 9, 0, 0));
                this.summonerRepository.save(summoner);
            }

            SummonerLeague summonerLeague = this.summonerLeagueRepository.findBySummonerAndQueueType(summoner, queueType);
            if (summonerLeague == null) {
                summonerLeague = new SummonerLeague();
                summonerLeague.setSummoner(summoner);
                summonerLeague.setQueueType(queueType);
                this.summonerLeagueRepository.save(summonerLeague);
            }
            summonerLeague.setHotStreak(sampleSummonerLeague.isHotStreak());
            summonerLeague.setWins(sampleSummonerLeague.getWins());
            summonerLeague.setVeteran(sampleSummonerLeague.isVeteran());
            summonerLeague.setLosses(sampleSummonerLeague.getLosses());
            summonerLeague.setInactive(sampleSummonerLeague.isInactive());
            summonerLeague.setFreshBlood(sampleSummonerLeague.isFreshBlood());
            summonerLeague.setLeaguePoints(sampleSummonerLeague.getLeaguePoints());
            summonerLeague.setLeagueTier(leagueTier);
            LeagueRank leagueRank = this.leagueRankRepository.findByKeyName(sampleSummonerLeague.getRank());
            if (leagueRank == null) {
                leagueRank = new LeagueRank();
                leagueRank.setKeyName(sampleSummonerLeague.getRank());
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
        SampleFeaturedGames sampleFeaturedGames = null;
        try {
            sampleFeaturedGames = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.FEATURED_GAMES
                            .replace("{{HOST}}", region.getHostName()),
                    true
            ), new TypeReference<SampleFeaturedGames>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            this.logger.error("No se ha podido retornar las partidas promocionadas " + e.getMessage());
        }
        FeaturedGameInterval featuredGameInterval = new FeaturedGameInterval();
        featuredGameInterval.setClientRefreshInterval(sampleFeaturedGames.getClientRefreshInterval());

        ArrayList<MatchGame> matchGames = new ArrayList<>();
        for (SampleFeaturedGameInfo sampleFeaturedGameInfo : sampleFeaturedGames.getGameList()) {
            sampleFeaturedGameInfo.getGameId();
            MatchGame matchGame = this.matchGameRepository.findByGameId(sampleFeaturedGameInfo.getGameId());
            if (matchGame == null) {
                matchGame = new MatchGame();
                matchGame.setGameId(sampleFeaturedGameInfo.getGameId());
                matchGame.setRegion(region);
                this.matchGameRepository.save(matchGame);
            }
            //TODO: completar esta clase
            for (SampleBannedChampion sampleBannedChampion : sampleFeaturedGameInfo.getBannedChampions()) {
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