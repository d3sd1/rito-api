package com.onlol.fetcher.api;

import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.ApiKey;
import com.onlol.fetcher.api.model.Summoner;
import com.onlol.fetcher.api.repository.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;

@Service
public class RestClient<T> {
    @Autowired
    private ApiKeyManager apiKeyManager;
    @Autowired
    private ApiKeyRepository apiKeyRepository;

    public Summoner get(String url) {

        ApiKey apiKey = this.apiKeyManager.getKey();
        if(apiKey == null) {
            return new Summoner();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey.getApiKey());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Summoner> resp = restTemplate.exchange(
                url, HttpMethod.GET, new HttpEntity(headers), new ParameterizedTypeReference<Summoner>() {
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
                return this.get(url);
        }
        return resp.getBody();
    }
}
