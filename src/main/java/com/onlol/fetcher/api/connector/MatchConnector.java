package com.onlol.fetcher.api.connector;

import com.onlol.fetcher.api.ApiKeyManager;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.api.repository.*;
import com.onlol.fetcher.api.sampleModel.SampleMatchGame;
import com.onlol.fetcher.api.sampleModel.SampleMatchList;
import com.onlol.fetcher.api.sampleModel.SampleMatchLists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchConnector {

    @Autowired
    private ApiKeyManager apiKeyManager;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private SeasonRepository seasonRepository;

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

    public List<MatchList> matchListByAccount(String encryptedAccountId) {
        ApiKey apiKey = this.apiKeyManager.getKey();
        if (apiKey == null) {
            return new ArrayList<>();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey.getApiKey());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SampleMatchLists> resp = restTemplate.exchange(
                V4.MATCHLIST_BY_ACCOUNT.replace("{{SUMMONER_ACCOUNT}}", encryptedAccountId),
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
        for(SampleMatchList sampleMatchList:sampleMatchLists.getMatches()) {
            MatchList matchList = this.matchListRepository.findByMatchGameIdAndSummonerAccountId(sampleMatchList.getGameId(), encryptedAccountId);
            if(matchList == null) {
                matchList = new MatchList();

                MatchGame matchGame = this.matchGameRepository.findByGameId(sampleMatchList.getGameId());
                if(matchGame == null) {
                    matchGame = new MatchGame();
                    matchGame.setGameId(sampleMatchList.getGameId());
                    this.matchGameRepository.save(matchGame);
                }

                matchList.setMatch(matchGame);

                /* Get platform */
                Platform platform = this.platformRepository.findByKeyName(sampleMatchList.getPlatformId());
                if(platform == null) {
                    Platform dbPlatform = new Platform();
                    dbPlatform.setKeyName(sampleMatchList.getPlatformId());
                    this.platformRepository.save(dbPlatform);
                    platform = dbPlatform;
                }
                matchList.setPlatform(platform);


                /* Get queue */
                Queue queue = this.queueRepository.findTopById(sampleMatchList.getQueue());
                if(queue == null) {
                    Queue dbQueue = new Queue();
                    dbQueue.setId(sampleMatchList.getQueue());

                    this.queueRepository.save(dbQueue);
                    queue = dbQueue;
                }
                matchList.setQueue(queue);


                /* Get role */
                Role role = this.roleRepository.findByKeyName(sampleMatchList.getRole());
                if(role == null) {
                    Role dbRole = new Role();
                    dbRole.setKeyName(sampleMatchList.getRole());

                    this.roleRepository.save(dbRole);
                    role = dbRole;
                }
                matchList.setRole(role);

                /* Get season */
                Season season = this.seasonRepository.findTopById(sampleMatchList.getSeason());
                if(season == null) {
                    Season dbSeason = new Season();
                    dbSeason.setId(sampleMatchList.getSeason());

                    this.seasonRepository.save(dbSeason);
                    season = dbSeason;
                }
                matchList.setSeason(season);

                /* Get lane */
                Lane lane = this.laneRepository.findByKeyName(sampleMatchList.getLane());
                if(lane == null) {
                    Lane dbLane = new Lane();
                    dbLane.setKeyName(sampleMatchList.getLane());

                    this.laneRepository.save(dbLane);
                    lane = dbLane;
                }
                matchList.setLane(lane);

                /*matchList.setChamp();
                * */
                matchList.setTimestamp((new Timestamp(sampleMatchList.getTimestamp()).toLocalDateTime()));
                matchList.setSummoner(summoner);
                this.matchListRepository.save(matchList);
            }
            matchLists.add(matchList);
        }
        //TODO: iterar para todas las partidas
        return matchLists;
    }



    public MatchGame match(Long gameId) {
        ApiKey apiKey = this.apiKeyManager.getKey();
        if (apiKey == null) {
            return new MatchGame();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey.getApiKey());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SampleMatchGame> resp = restTemplate.exchange(
                V4.MATCHES.replace("{{GAME_ID}}", gameId.toString()),
                HttpMethod.GET, new HttpEntity(headers),
                new ParameterizedTypeReference<SampleMatchGame>() {
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
                return this.match(gameId);
        }

        SampleMatchGame matchGame = resp.getBody();

        System.out.println("PARTTIDA: " + matchGame);
        //TODO: recuperar partida aqui
        return null;
    }
}
