package com.onlol.fetcher.api;

import com.fasterxml.classmate.GenericType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.ApiKey;
import com.onlol.fetcher.api.model.Summoner;
import com.onlol.fetcher.api.model.Version;
import com.onlol.fetcher.api.repository.ApiKeyRepository;
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

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Service
public class ApiConnector {
    @Autowired
    private ApiKeyManager apiKeyManager;

    @Autowired
    private LogService logService;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    public String get(String url) {
        return this.get(url, false);
    }

    public String get(String url, boolean needsApiKey) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity requestEntity = null;
        ApiKey apiKey = null;
        if (needsApiKey) {
            apiKey = this.apiKeyManager.getKey();
            if (apiKey == null) {
                // Enqueue after 60s if no api keys present
                this.logService.error("No api keys found. Sleeping 60 seconds.");
                try {
                    TimeUnit.SECONDS.sleep(60);
                } catch (InterruptedException e) {
                }
                return this.get(url, needsApiKey);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Riot-Token", apiKey.getApiKey());
            requestEntity = new HttpEntity(headers);
        }
        ResponseEntity<String> resp = null;
        try {
            resp = restTemplate.exchange(
                    url,
                    HttpMethod.GET, requestEntity,
                    String.class);
        } catch (HttpClientErrorException e) {
            switch (e.getStatusCode().value()) {
                case 401:
                    if (needsApiKey) {
                        apiKey.setBanned(true);
                        apiKey.setValid(false);
                        this.apiKeyRepository.save(apiKey);
                    }
                    break;
                case 403:
                    this.logService.debug("Forbidden URL: " + url);
                    break;
/* TODO: aqui hacer cosetes
                case 404:
                    matchGame.setGameId(gameId);
                    matchGame.setRetrieved(true);
                    matchGame.setRetrieving(false);
                    matchGame.setExpired(true);
                    this.matchGameRepository.save(matchGame);
                    return matchGame;*/
                case 429:
                    if (needsApiKey) {
                        apiKey.setBanned(true);
                        apiKey.setValid(true);
                        this.apiKeyRepository.save(apiKey);
                    }
                    return this.get(url, needsApiKey);
                case 503:
                    e.printStackTrace();
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    this.logService.warning("Api seems to be down on endpoint " + url);
                    return this.get(url, needsApiKey);

            }
        }
        return resp == null ? null : resp.getBody();
    }

}

