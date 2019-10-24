package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.ddragon.connector.GameChampionConnector;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchConnector {

    @Autowired
    private GameChampionConnector gameChampionConnector;

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
    private LaneRepository laneRepository;

    @Autowired
    private GameQueueRepository gameQueueRepository;

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
        ApiMatchlistDto apiMatchlistDto;
        /* TODO
        try {

            System.out.println(summoner);
            System.out.println(V4.MATCHLIST_BY_ACCOUNT
                    .replace("{{SUMMONER_ACCOUNT}}", summoner.getAccountId())
                    .replace("{{HOST}}", summoner.getRegion().getHostName())
                    .replace("{{BEGIN_INDEX}}", beginIndex.toString()));
            apiMatchlistDto = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.MATCHLIST_BY_ACCOUNT
                            .replace("{{SUMMONER_ACCOUNT}}", summoner.getAccountId())
                            .replace("{{HOST}}", summoner.getRegion().getHostName())
                            .replace("{{BEGIN_INDEX}}", beginIndex.toString()),
                    true
            ), new TypeReference<ApiMatchlistDto>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            apiMatchlistDto = null;
            this.logger.error("No se ha podido retornar las partidas del invocador " + summoner.getName());
        }
        if (apiMatchlistDto == null) {
            return new ArrayList<>();
        }

        List<MatchList> matchLists = new ArrayList<>();

        for (ApiMatchReferenceDTO apiMatchReferenceDto : apiMatchlistDto.getMatches()) {
            MatchList matchList = this.matchListRepository.findByMatchGameIdAndSummonerAccountId(apiMatchReferenceDto.getGameId(), summoner.getAccountId());
            if (matchList == null) {
                matchList = new MatchList();

                MatchGame matchGame = this.matchGameRepository.findByGameId(apiMatchReferenceDto.getGameId());
                if (matchGame == null) {
                    matchGame = new MatchGame();
                    matchGame.setGameId(apiMatchReferenceDto.getGameId());
                    matchGame.setRegion(summoner.getRegion());
                    this.matchGameRepository.save(matchGame);
                }

                matchList.setMatch(matchGame);

                /* Get platform constant *
                Region region = this.regionRepository.findByServicePlatform(apiMatchReferenceDto.getPlatformId());
                matchList.setRegion(region);


                /* Get queue *
                GameQueue gameQueue = this.gameQueueRepository.findTopByQueueId(apiMatchReferenceDto.getQueue());
                if (gameQueue == null) {
                    GameQueue dbGameQueue = new GameQueue();
                    dbGameQueue.setQueueId(apiMatchReferenceDto.getQueue());

                    this.gameQueueRepository.save(dbGameQueue);
                    gameQueue = dbGameQueue;
                }
                matchList.setGameQueue(gameQueue);


                /* Get role *
                Role role = this.roleRepository.findByKeyName(apiMatchReferenceDto.getRole());
                if (role == null) {
                    Role dbRole = new Role();
                    dbRole.setKeyName(apiMatchReferenceDto.getRole());

                    if (dbRole.getKeyName() != null) {
                        this.roleRepository.save(dbRole);
                        role = dbRole;
                    }
                }
                matchList.setRole(role);

                /* Get season *
                GameSeason gameSeason = this.gameSeasonRepository.findTopById(apiMatchReferenceDto.getSeason());
                if (gameSeason == null) {
                    GameSeason dbGameSeason = new GameSeason();
                    dbGameSeason.setId(apiMatchReferenceDto.getSeason());

                    this.gameSeasonRepository.save(dbGameSeason);
                    gameSeason = dbGameSeason;
                }
                matchList.setGameSeason(gameSeason);

                /* Get lane *
                Lane lane = this.laneRepository.findByKeyName(apiMatchReferenceDto.getLane());
                if (lane == null && apiMatchReferenceDto.getLane() != null) {
                    Lane dbLane = new Lane();
                    dbLane.setKeyName(apiMatchReferenceDto.getLane());

                    this.laneRepository.save(dbLane);
                    lane = dbLane;
                }
                matchList.setLane(lane);


                /* Get champ *
                Champion champion = this.championRepository.findByChampId(apiMatchReferenceDto.getChampion());
                if (champion != null) {
                    matchList.setChamp(champion);
                } else {
                    this.gameChampionConnector.champions();
                    this.matchListByAccount(summoner, beginIndex);
                }
                matchList.setLane(lane);

                matchList.setTimestamp((new Timestamp(apiMatchReferenceDto.getTimestamp()).toLocalDateTime()));
                matchList.setSummoner(summoner);
                this.matchListRepository.save(matchList);
            }
            matchLists.add(matchList);
        }
        // Iterar todas las partidas, cogiendo como primer resultado la siguiente partida a la última almacenada
        if (apiMatchlistDto.getEndIndex() < apiMatchlistDto.getTotalGames()) {
            this.matchListByAccount(summoner, apiMatchlistDto.getEndIndex() + 1);
        }*/
        return new ArrayList<>();
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
            apiMatchDTO = null;
        } catch (Exception e) {
            apiMatchDTO = null;
            this.logger.error("No se ha podido recuperar el match " + matchGame.getGameId().toString());
        }

        if (apiMatchDTO == null) { // Game does not exists so... Update if (if pos) on the db

            return new MatchGame();
        }

        matchGame.setGameId(apiMatchDTO.getGameId());
        matchGame.setGameCreation(new

                Timestamp(apiMatchDTO.getGameCreation()).

                toLocalDateTime());
        matchGame.setGameDuration(apiMatchDTO.getGameDuration());
        matchGame.setGameVersion(apiMatchDTO.getGameVersion());


        /* Get queue */
        GameQueue gameQueue = this.gameQueueRepository.findTopByQueueId(apiMatchDTO.getQueueId());
        if (gameQueue == null) {
            GameQueue dbGameQueue = new GameQueue();
            dbGameQueue.setQueueId(apiMatchDTO.getQueueId());

            this.gameQueueRepository.save(dbGameQueue);
            gameQueue = dbGameQueue;
        }
        matchGame.setGameQueue(gameQueue);


        /* Get season */
        GameSeason gameSeason = this.gameSeasonRepository.findTopById(apiMatchDTO.getSeasonId());
        if (gameSeason == null) {
            GameSeason dbGameSeason = new GameSeason();
            dbGameSeason.setId(apiMatchDTO.getSeasonId());

            this.gameSeasonRepository.save(dbGameSeason);
            gameSeason = dbGameSeason;
        }
        matchGame.setGameSeason(gameSeason);


        /* Get game map */
        GameMap gameMap = this.gameMapRepository.findTopByMapId(apiMatchDTO.getMapId());
        if (gameMap == null) {
            GameMap dbGameMap = new GameMap();
            dbGameMap.setMapId(apiMatchDTO.getSeasonId());

            this.gameMapRepository.save(dbGameMap);
            gameMap = dbGameMap;
        }
        matchGame.setGameMap(gameMap);


        /* Get game mode */
        GameMode gameMode = this.gameModeRepository.findByGameMode(apiMatchDTO.getGameMode());
        if (gameMode == null) {
            GameMode dbGameMode = new GameMode();
            dbGameMode.setGameMode(apiMatchDTO.getGameMode());

            this.gameModeRepository.save(dbGameMode);
            gameMode = dbGameMode;
        }
        matchGame.setGameMode(gameMode);


        /* Get game type */
        GameType gameType = this.gameTypeRepository.findByGameType(apiMatchDTO.getGameType());
        if (gameType == null) {
            GameType dbGameType = new GameType();
            dbGameType.setGameType(apiMatchDTO.getGameType());

            this.gameTypeRepository.save(dbGameType);
            gameType = dbGameType;
        }
        matchGame.setGameType(gameType);

        matchGame.setRetrieving(false);
        matchGame.setRetrieved(true);

        this.matchGameRepository.save(matchGame);

        /* Add summoners to update *
        for (
                ApiParticipantIdentityDTO apiParticipantIdentityDto : apiMatchDTO.getParticipantIdentities()) {

            apiParticipantIdentityDto.getPlayer().getSummonerId();
            Summoner dbSummoner = this.summonerRepository.findByAccountId(apiParticipantIdentityDto.getPlayer().getAccountId());
            if (dbSummoner == null) {
                dbSummoner = new Summoner();
                //TODO dbSummoner.setAccountId(apiParticipantIdentityDto.getPlayer().getAccountId());
                //TODO dbSummoner.setId(apiParticipantIdentityDto.getPlayer().getSummonerId());
                dbSummoner.setRegion(matchGame.getRegion());
                dbSummoner.setLastTimeUpdated(LocalDateTime.of(2010, 9, 9, 0, 0));
                dbSummoner.setName(apiParticipantIdentityDto.getPlayer().getSummonerName());
                this.summonerRepository.save(dbSummoner);
            }
        }

        /* Match game stats */

        for (
                ApiTeamStatsDTO apiTeamStatsDto : apiMatchDTO.getTeams()) {
            MatchGameTeam matchGameTeam = this.matchGameTeamRepository.findByTeamId(apiTeamStatsDto.getTeamId());
            if (matchGameTeam == null) {
                matchGameTeam = new MatchGameTeam();
                matchGameTeam.setTeamId(apiTeamStatsDto.getTeamId());
                matchGameTeam = this.matchGameTeamRepository.save(matchGameTeam);
            }
            MatchGameTeamStats matchGameTeamStats = this.matchGameTeamStatsRepository.
                    findByGameIdAndTeam(apiMatchDTO.getGameId(), matchGameTeam);

            if (matchGameTeamStats == null) {
                MatchGameTeamStats dbMatchGameTeamStats = new MatchGameTeamStats();
                dbMatchGameTeamStats.setGameId(apiMatchDTO.getGameId());
                dbMatchGameTeamStats.setTeam(matchGameTeam);

                matchGameTeamStats = this.matchGameTeamStatsRepository.save(dbMatchGameTeamStats);
            }
            matchGameTeamStats.setFirstDragon(apiTeamStatsDto.isFirstDragon());
            matchGameTeamStats.setFirstInhibitor(apiTeamStatsDto.isFirstInhibitor());
            matchGameTeamStats.setFirstRiftHerald(apiTeamStatsDto.isFirstRiftHerald());
            matchGameTeamStats.setFirstBaron(apiTeamStatsDto.isFirstBaron());
            matchGameTeamStats.setFirstBlood(apiTeamStatsDto.isFirstBlood());
            matchGameTeamStats.setFirstTower(apiTeamStatsDto.isFirstTower());
            matchGameTeamStats.setBaronKills(apiTeamStatsDto.getBaronKills());
            matchGameTeamStats.setRiftHeraldKills(apiTeamStatsDto.getRiftHeraldKills());
            matchGameTeamStats.setVilemawKills(apiTeamStatsDto.getVilemawKills());
            matchGameTeamStats.setInhibitorKills(apiTeamStatsDto.getInhibitorKills());
            matchGameTeamStats.setTowerKills(apiTeamStatsDto.getTowerKills());
            matchGameTeamStats.setDragonKills(apiTeamStatsDto.getDragonKills());
            matchGameTeamStats.setDominionVictoryScore(apiTeamStatsDto.getDominionVictoryScore());
            matchGameTeamStats.setWon(apiTeamStatsDto.getWin().equalsIgnoreCase("Win"));

            /* Fill bans for team just if not set (IMPORTANT) */

            if (matchGameTeamStats.getBans() == null) {
                ArrayList<MatchGameTeamBan> matchGameTeamBans = new ArrayList<>();
                for (ApiTeamBansDTO apiTeamBansDto : apiTeamStatsDto.getBans()) {
                    MatchGameTeamBan matchGameTeamBan = new MatchGameTeamBan();
                    matchGameTeamBan.setPickTurn(apiTeamBansDto.getPickTurn());
                    matchGameTeamBan.setChampion(this.championRepository.findByChampId(apiTeamBansDto.getChampionId()));
                    matchGameTeamBan = this.matchGameTeamBanRepository.save(matchGameTeamBan);
                    matchGameTeamBans.add(matchGameTeamBan);
                }
                matchGameTeamStats.setBans(matchGameTeamBans);
            }


            this.matchGameTeamStatsRepository.save(matchGameTeamStats);
        }

        /* Fill participants */
        for (
                ApiParticipantIdentityDTO apiParticipantIdentityDto : apiMatchDTO.getParticipantIdentities()) {
            Optional<Summoner> opsummoner = this.summonerRepository.findById(apiParticipantIdentityDto.getPlayer().getSummonerId());
            Summoner summoner;
            if (opsummoner.isEmpty()) {
                summoner = new Summoner();
                summoner.setRegion(matchGame.getRegion());
                //TODO summoner.setId(apiParticipantIdentityDto.getPlayer().getSummonerId());
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
