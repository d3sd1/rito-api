package com.onlol.fetcher.api.connector;

import com.onlol.fetcher.api.ApiKeyManager;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.ApiKey;
import com.onlol.fetcher.api.model.Summoner;
import com.onlol.fetcher.api.model.SummonerNameHistorical;
import com.onlol.fetcher.api.repository.ApiKeyRepository;
import com.onlol.fetcher.api.repository.SummonerNameHistoricalRepository;
import com.onlol.fetcher.api.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Summoner byName(String name) {
        ApiKey apiKey = this.apiKeyManager.getKey();
        if (apiKey == null) {
            return new Summoner();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey.getApiKey());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Summoner> resp = restTemplate.exchange(
                V4.SUMMONERS_BY_NAME.replace("{{SUMMONER_NAME}}", name),
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
                return this.byName(name);
        }
        Summoner summoner = resp.getBody();
        if(summoner != null) {
            summoner.setLastTimeUpdated(LocalDateTime.now());
            summoner = this.summonerRepository.save(summoner);
            // Update historical name if needed
            if(this.summonerNameHistoricalRepository.findTopByNameAndSummoner(summoner.getName(), summoner) == null) {
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
        if(summoner != null) {
            summoner.setLastTimeUpdated(LocalDateTime.now());
            summoner = this.summonerRepository.save(summoner);
            // Update historical name if needed
            if(this.summonerNameHistoricalRepository.findTopByNameAndSummoner(summoner.getName(), summoner) == null) {
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
        if(summoner != null) {
            summoner.setLastTimeUpdated(LocalDateTime.now());
            summoner = this.summonerRepository.save(summoner);
            // Update historical name if needed
            if(this.summonerNameHistoricalRepository.findTopByNameAndSummoner(summoner.getName(), summoner) == null) {
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
        if(summoner != null) {
            summoner.setLastTimeUpdated(LocalDateTime.now());
            summoner = this.summonerRepository.save(summoner);
            // Update historical name if needed
            if(this.summonerNameHistoricalRepository.findTopByNameAndSummoner(summoner.getName(), summoner) == null) {
                SummonerNameHistorical summonerNameHistorical = new SummonerNameHistorical();
                summonerNameHistorical.setName(summoner.getName());
                summonerNameHistorical.setSummoner(summoner);
                this.summonerNameHistoricalRepository.save(summonerNameHistorical);
            }
        }
        return summoner;
    }
}
