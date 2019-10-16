package com.onlol.fetcher.api.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.ApiKeyManager;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.api.repository.*;
import com.onlol.fetcher.api.sampleModel.SampleSummonerChampionMastery;
import com.onlol.fetcher.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class SummonerConnector {

    @Autowired
    private ApiKeyManager apiKeyManager;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private SummonerNameHistoricalRepository summonerNameHistoricalRepository;

    @Autowired
    private SummonerChampionMasteryRepository summonerChampionMasteryRepository;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private LogService logger;

    @Autowired
    private ObjectMapper jacksonMapper;

    public Summoner byName(String name, String regionName) {
        Region region = this.regionRepository.findByServiceRegion(regionName);
        if (region == null) {
            this.logger.error("Region not found on summonerByName: " + regionName);
            return null;
        }
        Summoner summoner = null;
        try {
            summoner = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.SUMMONERS_BY_NAME
                            .replace("{{SUMMONER_NAME}}", name)
                            .replace("{{HOST}}", region.getHostName()),
                    true
            ), new TypeReference(){});
        } catch (IOException e) {
            this.logger.error("No se ha podido retornar al invocador " + name);
        }

        if (summoner != null) {
            summoner.setLastTimeUpdated(LocalDateTime.now());
            summoner = this.summonerRepository.save(summoner);
            // Update historical name if needed
            if (this.summonerNameHistoricalRepository.findTopByNameAndSummoner(summoner.getName(), summoner) == null) {
                SummonerNameHistorical summonerNameHistorical = new SummonerNameHistorical();
                summonerNameHistorical.setName(summoner.getName());
                summonerNameHistorical.setSummoner(summoner);
                this.summonerNameHistoricalRepository.save(summonerNameHistorical);
            }
        }
        return summoner;
    }

    public Summoner byPuuid(String puuid) {
        ApiKey apiKey = this.apiKeyManager.getKey();
        if (apiKey == null) {
            return new Summoner();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey.getApiKey());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Summoner> resp = restTemplate.exchange(
                V4.SUMMONERS_BY_PUUID.replace("{{SUMMONER_PUUID}}", puuid),
                HttpMethod.GET, new HttpEntity(headers),
                new ParameterizedTypeReference<Summoner>() {
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
                return this.byPuuid(puuid);
        }
        Summoner summoner = resp.getBody();
        if (summoner != null) {
            summoner.setLastTimeUpdated(LocalDateTime.now());
            summoner = this.summonerRepository.save(summoner);
            // Update historical name if needed
            if (this.summonerNameHistoricalRepository.findTopByNameAndSummoner(summoner.getName(), summoner) == null) {
                SummonerNameHistorical summonerNameHistorical = new SummonerNameHistorical();
                summonerNameHistorical.setName(summoner.getName());
                summonerNameHistorical.setSummoner(summoner);
                this.summonerNameHistoricalRepository.save(summonerNameHistorical);
            }
        }
        return summoner;
    }

    public Summoner byAccount(String summonerAccount) {
        ApiKey apiKey = this.apiKeyManager.getKey();
        if (apiKey == null) {
            return new Summoner();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey.getApiKey());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Summoner> resp = restTemplate.exchange(
                V4.SUMMONERS_BY_ACCOUNT.replace("{{SUMMONER_ACCOUNT}}", summonerAccount),
                HttpMethod.GET, new HttpEntity(headers),
                new ParameterizedTypeReference<Summoner>() {
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
                return this.byAccount(summonerAccount);
        }
        Summoner summoner = resp.getBody();
        if (summoner != null) {
            summoner.setLastTimeUpdated(LocalDateTime.now());
            summoner = this.summonerRepository.save(summoner);
            // Update historical name if needed
            if (this.summonerNameHistoricalRepository.findTopByNameAndSummoner(summoner.getName(), summoner) == null) {
                SummonerNameHistorical summonerNameHistorical = new SummonerNameHistorical();
                summonerNameHistorical.setName(summoner.getName());
                summonerNameHistorical.setSummoner(summoner);
                this.summonerNameHistoricalRepository.save(summonerNameHistorical);
            }
        }
        return summoner;
    }


    public Summoner bySummonerId(String id) {

        ApiKey apiKey = this.apiKeyManager.getKey();
        if (apiKey == null) {
            return new Summoner();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey.getApiKey());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Summoner> resp = restTemplate.exchange(
                V4.SUMMONERS_BY_ID.replace("{{SUMMONER_ID}}", String.join(",", id)),
                HttpMethod.GET, new HttpEntity(headers),
                new ParameterizedTypeReference<Summoner>() {
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
                return this.bySummonerId(id);
        }
        Summoner summoner = resp.getBody();
        if (summoner != null) {
            summoner.setLastTimeUpdated(LocalDateTime.now());
            summoner = this.summonerRepository.save(summoner);
            // Update historical name if needed
            if (this.summonerNameHistoricalRepository.findTopByNameAndSummoner(summoner.getName(), summoner) == null) {
                SummonerNameHistorical summonerNameHistorical = new SummonerNameHistorical();
                summonerNameHistorical.setName(summoner.getName());
                summonerNameHistorical.setSummoner(summoner);
                this.summonerNameHistoricalRepository.save(summonerNameHistorical);
            }
        }
        return summoner;
    }

    public ArrayList<SummonerChampionMastery> championMastery(Summoner summoner) {

        ApiKey apiKey = this.apiKeyManager.getKey();
        if (apiKey == null) {
            return new ArrayList<>();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey.getApiKey());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList<SampleSummonerChampionMastery>> resp = restTemplate.exchange(
                V4.SUMMONER_CHAMPION_MASTERY
                        .replace("{{SUMMONER_ID}}", summoner.getId())
                        .replace("{{HOST}}", summoner.getRegion().getHostName()),
                HttpMethod.GET, new HttpEntity(headers),
                new ParameterizedTypeReference<ArrayList<SampleSummonerChampionMastery>>() {
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
                return this.championMastery(summoner);
        }

        ArrayList<SampleSummonerChampionMastery> sampleSummonerChampionMasteries = resp.getBody();
        ArrayList<SummonerChampionMastery> summonerChampionMasteries = new ArrayList<>();
        for (SampleSummonerChampionMastery sampleSummonerChampionMastery : sampleSummonerChampionMasteries) {
            SummonerChampionMastery summonerChampionMastery = this.summonerChampionMasteryRepository.findBySummoner(summoner);
            if (summonerChampionMastery == null) {
                summonerChampionMastery = new SummonerChampionMastery();
            }
            summonerChampionMastery.setSummoner(summoner);
            summonerChampionMastery.setChampion(this.championRepository.findByChampId(sampleSummonerChampionMastery.getChampionId()));
            summonerChampionMastery.setChampionLevel(sampleSummonerChampionMastery.getChampionLevel());
            summonerChampionMastery.setChampionPoints(sampleSummonerChampionMastery.getChampionPoints());
            summonerChampionMastery.setChampionPointsSinceLastLevel(sampleSummonerChampionMastery.getChampionPointsSinceLastLevel());
            summonerChampionMastery.setChampionPointsUntilNextLevel(sampleSummonerChampionMastery.getChampionPointsUntilNextLevel());
            summonerChampionMastery.setChestGranted(sampleSummonerChampionMastery.isChestGranted());
            summonerChampionMastery.setLastPlayTime(new Timestamp(sampleSummonerChampionMastery.getLastPlayTime()).toLocalDateTime());
            summonerChampionMastery.setTokensEarned(sampleSummonerChampionMastery.getTokensEarned());
            this.summonerChampionMasteryRepository.save(summonerChampionMastery);
        }
        return summonerChampionMasteries;
    }
}
