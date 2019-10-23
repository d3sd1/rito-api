package com.onlol.fetcher.api;

import com.onlol.fetcher.api.exceptions.MatchNotfoundException;
import com.onlol.fetcher.api.model.ApiKey;
import com.onlol.fetcher.api.repository.ApiKeyRepository;
import com.onlol.fetcher.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
public class ApiConnector {
    @Autowired
    private ApiKeyManager apiKeyManager;

    @Autowired
    private LogService logService;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    public String get(String url) throws MatchNotfoundException {
        return this.get(url, false, Byte.decode("0"));
    }

    public String get(String url, boolean needsApiKey) throws MatchNotfoundException {
        return this.get(url, needsApiKey, Byte.decode("0"));
    }

    public String get(String url, boolean needsApiKey, byte attempts) throws MatchNotfoundException {
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
                return this.get(url, needsApiKey, ++attempts);
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
                case 404:
                    this.logService.info("Unknown url: " + url);
                    throw new MatchNotfoundException();
                case 429:
                    if (needsApiKey) {
                        apiKey.setBanned(true);
                        apiKey.setValid(true);
                        this.apiKeyRepository.save(apiKey);
                    }
                    return this.get(url, needsApiKey, ++attempts);

            }
        } catch (HttpServerErrorException e) {
            switch (e.getStatusCode().value()) {
                case 503:
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    // Prevent cycle. Retry only 3 times just in case.
                    if (attempts < 3) {
                        this.logService.warning("Api seems to be down on endpoint " + url + " with exception " + e.getMessage());
                        return this.get(url, needsApiKey, ++attempts);
                    } else {
                        return null;
                    }

            }
        } catch (ResourceAccessException e) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            // Prevent cycle. Retry only 3 times just in case.
            if (attempts < 3) {
                this.logService.warning("Api got temporal troubles resolving " + url + " with exception " + e.getMessage());
                return this.get(url, needsApiKey, ++attempts);
            } else {
                return null;
            }
        }
        //System.out.println(resp.getHeaders()); TODO: best effort - detectar limites y almacenarlos en db, almacenando llamadas y tipos de llamadas?
        return resp == null ? null : resp.getBody();
    }

}

