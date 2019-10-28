package com.onlol.fetcher.api.filler;

import com.onlol.fetcher.api.model.ApiMatchDTO;
import com.onlol.fetcher.api.model.ApiMatchReferenceDTO;
import com.onlol.fetcher.api.model.ApiSummonerDTO;
import com.onlol.fetcher.ddragon.filler.GameDataFiller;
import com.onlol.fetcher.ddragon.filler.GameInfoFiller;
import com.onlol.fetcher.model.MatchGame;
import com.onlol.fetcher.model.MatchList;
import com.onlol.fetcher.repository.MatchGameRepository;
import com.onlol.fetcher.repository.MatchListRepository;
import com.onlol.fetcher.repository.RegionRepository;
import com.onlol.fetcher.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchFiller {
    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private MatchGameRepository matchGameRepository;

    @Autowired
    private MatchListRepository matchListRepository;

    @Autowired
    private GameDataFiller gameDataFiller;

    @Autowired
    private GameInfoFiller gameInfoFiller;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SummonerFiller summonerFiller;


    public MatchList fillMatchListGame(ApiMatchReferenceDTO apiMatchReferenceDto, ApiSummonerDTO apiSummonerDTO) {
/* TODO
        Summoner summoner = this.summonerFiller.fillSummoner(apiSummonerDTO);
        MatchList matchList = this.matchListRepository.findByMatchGameIdAndSummoner(apiMatchReferenceDto.getGameId(), summoner);
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
            matchList.setRegion(this.regionRepository.findByServicePlatform(apiMatchReferenceDto.getPlatformId()));

            /* Get queue *
            matchList.setGameQueue(this.gameDataFiller.fillGameQueue(apiMatchReferenceDto.getQueue()));

            /* Get role *
            matchList.setGameRole(this.gameDataFiller.fillGameRole(apiMatchReferenceDto.getRole()));

            /* Get season *
            matchList.setGameSeason(this.gameInfoFiller.fillGameSeason(apiMatchReferenceDto.getSeason()));

            /* Get lane *
            matchList.setGameLane(this.gameInfoFiller.fillGameLane(apiMatchReferenceDto.getLane()));

            /* Get champ *
            matchList.setChamp(this.gameDataFiller.fillChampion(apiMatchReferenceDto.getChampion()));

            matchList.setTimestamp((new Timestamp(apiMatchReferenceDto.getTimestamp()).toLocalDateTime()));
            matchList.setSummoner(summoner);
            this.matchListRepository.save(matchList);
        }
        return matchList;*/
        return null;
    }

    public MatchGame fillMatchGame(ApiMatchDTO apiMatchDTO) {
/*TODO
        matchGame.setGameId(apiMatchDTO.getGameId());
        matchGame.setGameCreation(new

                Timestamp(apiMatchDTO.getGameCreation()).

                toLocalDateTime());
        matchGame.setGameDuration(apiMatchDTO.getGameDuration());
        matchGame.setGameVersion(apiMatchDTO.getGameVersion());


        /* Get queue *
        GameQueue gameQueue = this.gameQueueRepository.findTopByQueueId(apiMatchDTO.getQueueId());
        if (gameQueue == null) {
            GameQueue dbGameQueue = new GameQueue();
            dbGameQueue.setQueueId(apiMatchDTO.getQueueId());

            this.gameQueueRepository.save(dbGameQueue);
            gameQueue = dbGameQueue;
        }
        matchGame.setGameQueue(gameQueue);


        /* Get season *
        GameSeason gameSeason = this.gameSeasonRepository.findTopById(apiMatchDTO.getSeasonId());
        if (gameSeason == null) {
            GameSeason dbGameSeason = new GameSeason();
            dbGameSeason.setId(apiMatchDTO.getSeasonId());

            this.gameSeasonRepository.save(dbGameSeason);
            gameSeason = dbGameSeason;
        }
        matchGame.setGameSeason(gameSeason);


        /* Get game map *
        GameMap gameMap = this.gameMapRepository.findTopByMapId(apiMatchDTO.getMapId());
        if (gameMap == null) {
            GameMap dbGameMap = new GameMap();
            dbGameMap.setMapId(apiMatchDTO.getSeasonId());

            this.gameMapRepository.save(dbGameMap);
            gameMap = dbGameMap;
        }
        matchGame.setGameMap(gameMap);


        /* Get game mode *
        GameMode gameMode = this.gameModeRepository.findByGameMode(apiMatchDTO.getGameMode());
        if (gameMode == null) {
            GameMode dbGameMode = new GameMode();
            dbGameMode.setGameMode(apiMatchDTO.getGameMode());

            this.gameModeRepository.save(dbGameMode);
            gameMode = dbGameMode;
        }
        matchGame.setGameMode(gameMode);


        /* Get game type *
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

        /* Match game stats *

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

            /* Fill bans for team just if not set (IMPORTANT) *

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

        /* Fill participants *
        for (
                ApiParticipantIdentityDTO apiParticipantIdentityDto : apiMatchDTO.getParticipantIdentities()) {
            Optional<Summoner> opsummoner = this.summonerRepository.findById(apiParticipantIdentityDto.getPlayer().getSummonerId());
            Summoner summoner;
            if (opsummoner.isEmpty()) {
                summoner = new Summoner();
                summoner.setRegion(matchGame.getRegion());
                summoner.setId(apiParticipantIdentityDto.getPlayer().getSummonerId());
                summoner = this.summonerRepository.save(summoner);
            } else {
                summoner = opsummoner.get();
            }
            MatchGameParticipant matchGameParticipant = this.matchGameParticipantRepository.
                    findBySummonerAndMatchGame(summoner, matchGame);
            if (matchGameParticipant == null) {
                matchGameParticipant = new MatchGameParticipant();
                matchGameParticipant.setSummoner(summoner);
                apiMatchDTO.get
                matchGameParticipant.setMatchGame(matchGame);
                //TODO: agregar todos los setter de matchGameParticipant
                matchGameParticipant = this.matchGameParticipantRepository.save(matchGameParticipant);
            }
            //matchGameParticipant.set

            this.matchGameParticipantRepository.save(matchGameParticipant);
        }*/
        return null;
    }
}
