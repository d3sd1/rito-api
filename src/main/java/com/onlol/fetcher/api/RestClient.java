package com.onlol.fetcher.api;

import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.Summoner;
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
    public T get(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("api_key", "RGAPI-a5435ca3-ad49-4a9d-97a1-c42341da504f");


        HttpEntity entity = new HttpEntity(headers);

        //TODO: quye funcione este typetoken
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Summoner> resp = restTemplate.exchange(
                url, HttpMethod.GET, entity, new ParameterizedTypeReference<Summoner>(){});
        return (T) resp.getBody();
    }
}
