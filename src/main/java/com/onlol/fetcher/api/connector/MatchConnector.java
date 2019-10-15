package com.onlol.fetcher.api.connector;

import com.onlol.fetcher.api.ApiKeyManager;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.api.repository.*;
import com.onlol.fetcher.api.sampleModel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private PlatformRepository platformRepository;

    @Autowired
    private MatchGameRepository matchGameRepository;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private MatchListRepository matchListRepository;

    public List<MatchList> matchListByAccount(String encryptedAccountId) { // WRAPPER
        return this.matchListByAccount(encryptedAccountId, 0L);
    }

    public List<MatchList> matchListByAccount(String encryptedAccountId, Long beginIndex) {
        ApiKey apiKey = this.apiKeyManager.getKey();
        if (apiKey == null) {
            return new ArrayList<>();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey.getApiKey());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SampleMatchLists> resp = restTemplate.exchange(
                V4.MATCHLIST_BY_ACCOUNT.replace("{{SUMMONER_ACCOUNT}}", encryptedAccountId)
                        .replace("{{BEGIN_INDEX}}", beginIndex.toString()),
                HttpMethod.GET, new HttpEntity(headers),
                new ParameterizedTypeReference<SampleMatchLists>() {
                });

        switch (resp.getStatusCode().value()) {
            case 401:
                apiKey.setBanned(true);
                apiKey.setValid(false);
                this.apiKeyRepository.save(apiKey);
                break;
            case 429:
                apiKey.setBanned(true);
                apiKey.setValid(true);
                this.apiKeyRepository.save(apiKey);
                return this.matchListByAccount(encryptedAccountId);
        }

        // Parse to match current model
        SampleMatchLists sampleMatchLists = resp.getBody();

        List<MatchList> matchLists = new ArrayList<>();

        Summoner summoner = this.summonerRepository.findByAccountId(encryptedAccountId);

        for (SampleMatchList sampleMatchList : sampleMatchLists.getMatches()) {
            MatchList matchList = this.matchListRepository.findByMatchGameIdAndSummonerAccountId(sampleMatchList.getGameId(), encryptedAccountId);
            if (matchList == null) {
                matchList = new MatchList();

                MatchGame matchGame = this.matchGameRepository.findByGameId(sampleMatchList.getGameId());
                if (matchGame == null) {
                    matchGame = new MatchGame();
                    matchGame.setGameId(sampleMatchList.getGameId());
                    this.matchGameRepository.save(matchGame);
                }

                matchList.setMatch(matchGame);

                /* Get platform */
                Platform platform = this.platformRepository.findByKeyName(sampleMatchList.getPlatformId());
                if (platform == null) {
                    Platform dbPlatform = new Platform();
                    dbPlatform.setKeyName(sampleMatchList.getPlatformId());
                    this.platformRepository.save(dbPlatform);
                    platform = dbPlatform;
                }
                matchList.setPlatform(platform);


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

                    this.roleRepository.save(dbRole);
                    role = dbRole;
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
                if(champion != null) {
                    matchList.setChamp(champion);
                }
                else {
                    // Actualizar campeones ya que falta alguno en la DB y volver al mismo proceso
                    this.ddragonConnector.champions();
                    this.matchListByAccount(encryptedAccountId,beginIndex);
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
            this.matchListByAccount(encryptedAccountId, sampleMatchLists.getEndIndex() + 1);
        }
        return matchLists;
    }


    // MÁS SOFISTICADO
    public MatchGame match(Long gameId) {
        ApiKey apiKey = this.apiKeyManager.getKey();
        if (apiKey == null) {
            return new MatchGame();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey.getApiKey());

        MatchGame matchGame = new MatchGame();
        SampleMatchGame sampleMatchGame = new SampleMatchGame();

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<SampleMatchGame> resp = restTemplate.exchange(
                    V4.MATCHES.replace("{{GAME_ID}}", gameId.toString()),
                    HttpMethod.GET, new HttpEntity(headers),
                    new ParameterizedTypeReference<SampleMatchGame>() {
                    });
            sampleMatchGame = resp.getBody();
        } catch (final HttpClientErrorException e) {
            switch (e.getStatusCode().value()) {
                case 401:
                    apiKey.setBanned(true);
                    apiKey.setValid(false);
                    this.apiKeyRepository.save(apiKey);
                    return matchGame;
                case 404:
                    matchGame.setGameId(gameId);
                    matchGame.setRetrieved(true);
                    matchGame.setRetrieving(false);
                    matchGame.setExpired(true);
                    this.matchGameRepository.save(matchGame);
                    return matchGame;
                case 429:
                    apiKey.setBanned(true);
                    apiKey.setValid(true);
                    this.apiKeyRepository.save(apiKey);
                    return this.match(gameId);
            }
        }

        matchGame = this.matchGameRepository.findByGameId(sampleMatchGame.getGameId());

        if (matchGame == null) {
            matchGame = new MatchGame();
        }
        matchGame.setGameId(sampleMatchGame.getGameId());
        matchGame.setGameCreation(new Timestamp(sampleMatchGame.getGameCreation()).toLocalDateTime());
        matchGame.setGameDuration(sampleMatchGame.getGameDuration());
        matchGame.setGameVersion(sampleMatchGame.getGameVersion());


        /* Get platform */
        Platform platform = this.platformRepository.findByKeyName(sampleMatchGame.getPlatformId());
        if (platform == null) {
            Platform dbPlatform = new Platform();
            dbPlatform.setKeyName(sampleMatchGame.getPlatformId());
            this.platformRepository.save(dbPlatform);
            platform = dbPlatform;
        }
        matchGame.setPlatform(platform);


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
        GameMap gameMap = this.gameMapRepository.findTopById(sampleMatchGame.getMapId());
        if (gameMap == null) {
            GameMap dbGameMap = new GameMap();
            dbGameMap.setId(sampleMatchGame.getSeasonId());

            this.gameMapRepository.save(dbGameMap);
            gameMap = dbGameMap;
        }
        matchGame.setGameMap(gameMap);


        /* Get game mode */
        GameMode gameMode = this.gameModeRepository.findByKeyName(sampleMatchGame.getGameMode());
        if (gameMode == null) {
            GameMode dbGameMode = new GameMode();
            dbGameMode.setKeyName(sampleMatchGame.getGameMode());

            this.gameModeRepository.save(dbGameMode);
            gameMode = dbGameMode;
        }
        matchGame.setGameMode(gameMode);


        /* Get game type */
        GameType gameType = this.gameTypeRepository.findByKeyName(sampleMatchGame.getGameType());
        if (gameType == null) {
            GameType dbGameType = new GameType();
            dbGameType.setKeyName(sampleMatchGame.getGameType());

            this.gameTypeRepository.save(dbGameType);
            gameType = dbGameType;
        }
        matchGame.setGameType(gameType);

        matchGame.setRetrieving(false);
        matchGame.setRetrieved(true);

        this.matchGameRepository.save(matchGame);

        /* Add summoners to update */
        for (SampleParticipantIdentity sampleParticipantIdentity : sampleMatchGame.getParticipantIdentities()) {

            sampleParticipantIdentity.getPlayer().getSummonerId();
            Summoner dbSummoner = this.summonerRepository.findByAccountId(sampleParticipantIdentity.getPlayer().getAccountId());
            if (dbSummoner == null) {
                dbSummoner = new Summoner();
                dbSummoner.setAccountId(sampleParticipantIdentity.getPlayer().getAccountId());
                dbSummoner.setId(sampleParticipantIdentity.getPlayer().getSummonerId());
                dbSummoner.setLastTimeUpdated(LocalDateTime.of(2010, 9, 9, 0, 0));
                dbSummoner.setName(sampleParticipantIdentity.getPlayer().getSummonerName());
                this.summonerRepository.save(dbSummoner);
            }
        }

        System.out.println("PARTTIDA: " + sampleMatchGame);
        return null;
    }
}
