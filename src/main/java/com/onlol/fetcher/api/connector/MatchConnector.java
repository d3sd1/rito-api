package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.ApiKeyManager;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.api.repository.*;
import com.onlol.fetcher.api.sampleModel.*;
import com.onlol.fetcher.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchConnector {

    @Autowired
    private DdragonConnector ddragonConnector;

    @Autowired
    private ApiKeyManager apiKeyManager;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private GameModeRepository gameModeRepository;

    @Autowired
    private GameTypeRepository gameTypeRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private GameMapRepository gameMapRepository;

    @Autowired
    private LaneRepository laneRepository;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private RoleRepository roleRepository;

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

    public List<MatchList> matchListByAccount(Summoner summoner) { // WRAPPER
        return this.matchListByAccount(summoner, 0L);
    }

    public List<MatchList> matchListByAccount(Summoner summoner, Long beginIndex) {
        SampleMatchLists sampleMatchLists;
        System.out.println(V4.MATCHLIST_BY_ACCOUNT);
        System.out.println(summoner);
        try {
            sampleMatchLists = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.MATCHLIST_BY_ACCOUNT
                            .replace("{{SUMMONER_ACCOUNT}}", summoner.getAccountId())
                            .replace("{{HOST}}", summoner.getRegion().getHostName())
                            .replace("{{BEGIN_INDEX}}", beginIndex.toString()),
                    true
            ), new TypeReference<SampleMatchLists>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            sampleMatchLists = null;
            this.logger.error("No se ha podido retornar las partidas del invocador " + summoner.getName());
        }
        if (sampleMatchLists == null) {
            return new ArrayList<>();
        }

        List<MatchList> matchLists = new ArrayList<>();

        for (SampleMatchList sampleMatchList : sampleMatchLists.getMatches()) {
            MatchList matchList = this.matchListRepository.findByMatchGameIdAndSummonerAccountId(sampleMatchList.getGameId(), summoner.getAccountId());
            if (matchList == null) {
                matchList = new MatchList();

                MatchGame matchGame = this.matchGameRepository.findByGameId(sampleMatchList.getGameId());
                if (matchGame == null) {
                    matchGame = new MatchGame();
                    matchGame.setGameId(sampleMatchList.getGameId());
                    this.matchGameRepository.save(matchGame);
                }

                matchList.setMatch(matchGame);

                /* Get platform constant */
                Region region = this.regionRepository.findByServicePlatform(sampleMatchList.getPlatformId());
                matchList.setRegion(region);


                /* Get queue */
                Queue queue = this.queueRepository.findTopByQueueId(sampleMatchList.getQueue());
                if (queue == null) {
                    Queue dbQueue = new Queue();
                    dbQueue.setQueueId(sampleMatchList.getQueue());

                    this.queueRepository.save(dbQueue);
                    queue = dbQueue;
                }
                matchList.setQueue(queue);


                /* Get role */
                Role role = this.roleRepository.findByKeyName(sampleMatchList.getRole());
                if (role == null) {
                    Role dbRole = new Role();
                    dbRole.setKeyName(sampleMatchList.getRole());

                    if (dbRole.getKeyName() != null) {
                        this.roleRepository.save(dbRole);
                        role = dbRole;
                    }
                }
                matchList.setRole(role);

                /* Get season */
                Season season = this.seasonRepository.findTopById(sampleMatchList.getSeason());
                if (season == null) {
                    Season dbSeason = new Season();
                    dbSeason.setId(sampleMatchList.getSeason());

                    this.seasonRepository.save(dbSeason);
                    season = dbSeason;
                }
                matchList.setSeason(season);

                /* Get lane */
                Lane lane = this.laneRepository.findByKeyName(sampleMatchList.getLane());
                if (lane == null) {
                    Lane dbLane = new Lane();
                    dbLane.setKeyName(sampleMatchList.getLane());

                    this.laneRepository.save(dbLane);
                    lane = dbLane;
                }
                matchList.setLane(lane);


                /* Get champ */
                Champion champion = this.championRepository.findByChampId(sampleMatchList.getChampion());
                if (champion != null) {
                    matchList.setChamp(champion);
                } else {
                    this.ddragonConnector.champions();
                    this.matchListByAccount(summoner, beginIndex);
                }
                matchList.setLane(lane);

                matchList.setTimestamp((new Timestamp(sampleMatchList.getTimestamp()).toLocalDateTime()));
                matchList.setSummoner(summoner);
                this.matchListRepository.save(matchList);
            }
            matchLists.add(matchList);
        }
        // Iterar todas las partidas, cogiendo como primer resultado la siguiente partida a la última almacenada
        if (sampleMatchLists.getEndIndex() < sampleMatchLists.getTotalGames()) {
            this.matchListByAccount(summoner, sampleMatchLists.getEndIndex() + 1);
        }
        return matchLists;
    }


    // MÁS SOFISTICADO
    public MatchGame match(Long gameId, Region region) {
        MatchGame matchGame;
        SampleMatchGame sampleMatchGame;

        try {
            sampleMatchGame = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.MATCHES.replace("{{GAME_ID}}", gameId.toString())
                            .replace("{{HOST}}", region.getHostName()),
                    true
            ), new TypeReference<SampleMatchLists>() {
            });
        } catch (IOException e) {
            sampleMatchGame = null;
            this.logger.error("No se ha podido recuperar el match " + gameId.toString());
        }

        if (sampleMatchGame == null) {
            return new MatchGame();
        }

        matchGame = this.matchGameRepository.findByGameId(sampleMatchGame.getGameId());

        if (matchGame == null) {
            matchGame = new MatchGame();
        }
        matchGame.setGameId(sampleMatchGame.getGameId());
        matchGame.setGameCreation(new

                Timestamp(sampleMatchGame.getGameCreation()).

                toLocalDateTime());
        matchGame.setGameDuration(sampleMatchGame.getGameDuration());
        matchGame.setGameVersion(sampleMatchGame.getGameVersion());


        /* Get platform constant */
        matchGame.setRegion(region);


        /* Get queue */
        Queue queue = this.queueRepository.findTopByQueueId(sampleMatchGame.getQueueId());
        if (queue == null) {
            Queue dbQueue = new Queue();
            dbQueue.setQueueId(sampleMatchGame.getQueueId());

            this.queueRepository.save(dbQueue);
            queue = dbQueue;
        }
        matchGame.setQueue(queue);


        /* Get season */
        Season season = this.seasonRepository.findTopById(sampleMatchGame.getSeasonId());
        if (season == null) {
            Season dbSeason = new Season();
            dbSeason.setId(sampleMatchGame.getSeasonId());

            this.seasonRepository.save(dbSeason);
            season = dbSeason;
        }
        matchGame.setSeason(season);


        /* Get game map */
        GameMap gameMap = this.gameMapRepository.findTopByMapId(sampleMatchGame.getMapId());
        if (gameMap == null) {
            GameMap dbGameMap = new GameMap();
            dbGameMap.setMapId(sampleMatchGame.getSeasonId());

            this.gameMapRepository.save(dbGameMap);
            gameMap = dbGameMap;
        }
        matchGame.setGameMap(gameMap);


        /* Get game mode */
        GameMode gameMode = this.gameModeRepository.findByGameMode(sampleMatchGame.getGameMode());
        if (gameMode == null) {
            GameMode dbGameMode = new GameMode();
            dbGameMode.setGameMode(sampleMatchGame.getGameMode());

            this.gameModeRepository.save(dbGameMode);
            gameMode = dbGameMode;
        }
        matchGame.setGameMode(gameMode);


        /* Get game type */
        GameType gameType = this.gameTypeRepository.findByGametype(sampleMatchGame.getGameType());
        if (gameType == null) {
            GameType dbGameType = new GameType();
            dbGameType.setGametype(sampleMatchGame.getGameType());

            this.gameTypeRepository.save(dbGameType);
            gameType = dbGameType;
        }
        matchGame.setGameType(gameType);

        matchGame.setRetrieving(false);
        matchGame.setRetrieved(true);

        this.matchGameRepository.save(matchGame);

        /* Add summoners to update */
        for (
                SampleParticipantIdentity sampleParticipantIdentity : sampleMatchGame.getParticipantIdentities()) {

            sampleParticipantIdentity.getPlayer().getSummonerId();
            Summoner dbSummoner = this.summonerRepository.findByAccountId(sampleParticipantIdentity.getPlayer().getAccountId());
            if (dbSummoner == null) {
                dbSummoner = new Summoner();
                dbSummoner.setAccountId(sampleParticipantIdentity.getPlayer().getAccountId());
                dbSummoner.setId(sampleParticipantIdentity.getPlayer().getSummonerId());
                dbSummoner.setRegion(region);
                dbSummoner.setLastTimeUpdated(LocalDateTime.of(2010, 9, 9, 0, 0));
                dbSummoner.setName(sampleParticipantIdentity.getPlayer().getSummonerName());
                this.summonerRepository.save(dbSummoner);
            }
        }

        /* Match game stats */

        for (
                SampleTeamStats sampleTeamStats : sampleMatchGame.getTeams()) {
            MatchGameTeam matchGameTeam = this.matchGameTeamRepository.findByTeamId(sampleTeamStats.getTeamId());
            if (matchGameTeam == null) {
                matchGameTeam = new MatchGameTeam();
                matchGameTeam.setTeamId(sampleTeamStats.getTeamId());
                matchGameTeam = this.matchGameTeamRepository.save(matchGameTeam);
            }
            MatchGameTeamStats matchGameTeamStats = this.matchGameTeamStatsRepository.
                    findByGameIdAndTeam(sampleMatchGame.getGameId(), matchGameTeam);

            if (matchGameTeamStats == null) {
                MatchGameTeamStats dbMatchGameTeamStats = new MatchGameTeamStats();
                dbMatchGameTeamStats.setGameId(sampleMatchGame.getGameId());
                dbMatchGameTeamStats.setTeam(matchGameTeam);

                matchGameTeamStats = this.matchGameTeamStatsRepository.save(dbMatchGameTeamStats);
            }
            matchGameTeamStats.setFirstDragon(sampleTeamStats.isFirstDragon());
            matchGameTeamStats.setFirstInhibitor(sampleTeamStats.isFirstInhibitor());
            matchGameTeamStats.setFirstRiftHerald(sampleTeamStats.isFirstRiftHerald());
            matchGameTeamStats.setFirstBaron(sampleTeamStats.isFirstBaron());
            matchGameTeamStats.setFirstBlood(sampleTeamStats.isFirstBlood());
            matchGameTeamStats.setFirstTower(sampleTeamStats.isFirstTower());
            matchGameTeamStats.setBaronKills(sampleTeamStats.getBaronKills());
            matchGameTeamStats.setRiftHeraldKills(sampleTeamStats.getRiftHeraldKills());
            matchGameTeamStats.setVilemawKills(sampleTeamStats.getVilemawKills());
            matchGameTeamStats.setInhibitorKills(sampleTeamStats.getInhibitorKills());
            matchGameTeamStats.setTowerKills(sampleTeamStats.getTowerKills());
            matchGameTeamStats.setDragonKills(sampleTeamStats.getDragonKills());
            matchGameTeamStats.setDominionVictoryScore(sampleTeamStats.getDominionVictoryScore());
            matchGameTeamStats.setWon(sampleTeamStats.getWin().equalsIgnoreCase("Win"));

            /* Fill bans for team just if not set (IMPORTANT) */

            if (matchGameTeamStats.getBans() == null) {
                ArrayList<MatchGameTeamBan> matchGameTeamBans = new ArrayList<>();
                for (SampleTeamBans sampleTeamBans : sampleTeamStats.getBans()) {
                    MatchGameTeamBan matchGameTeamBan = new MatchGameTeamBan();
                    matchGameTeamBan.setPickTurn(sampleTeamBans.getPickTurn());
                    matchGameTeamBan.setChampion(this.championRepository.findByChampId(sampleTeamBans.getChampionId()));
                    matchGameTeamBan = this.matchGameTeamBanRepository.save(matchGameTeamBan);
                    matchGameTeamBans.add(matchGameTeamBan);
                }
                matchGameTeamStats.setBans(matchGameTeamBans);
            }


            this.matchGameTeamStatsRepository.save(matchGameTeamStats);
        }

        /* Fill participants */
        for (
                SampleParticipantIdentity sampleParticipantIdentity : sampleMatchGame.getParticipantIdentities()) {
            Optional<Summoner> opsummoner = this.summonerRepository.findById(sampleParticipantIdentity.getPlayer().getSummonerId());
            Summoner summoner;
            if (opsummoner.isEmpty()) {
                summoner = new Summoner();
                summoner.setRegion(region);
                summoner.setId(sampleParticipantIdentity.getPlayer().getSummonerId());
                summoner = this.summonerRepository.save(summoner);
            } else {
                summoner = opsummoner.get();
            }
            MatchGameParticipant matchGameParticipant = this.matchGameParticipantRepository.
                    findBySummonerAndMatchGame(summoner, matchGame);
            if (matchGameParticipant == null) {
                matchGameParticipant = new MatchGameParticipant();
                matchGameParticipant.setSummoner(summoner);
                matchGameParticipant.setMatchGame(matchGame);
                matchGameParticipant = this.matchGameParticipantRepository.save(matchGameParticipant);
            }
            //matchGameParticipant.set

            this.matchGameParticipantRepository.save(matchGameParticipant);
        }
        return null;
    }
}
