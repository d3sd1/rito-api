package com.onlol.fetcher.api.connector;

import com.onlol.fetcher.api.ApiKeyManager;
import com.onlol.fetcher.api.RestClient;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.Summoner;
import com.onlol.fetcher.api.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummonerConnector {

    @Autowired
    private RestClient<Summoner> summonerRestClient;

    public void byAccount() {

    }
    public Summoner byName(String name) {
        return this.summonerRestClient.get(V4.SUMMONERS_BY_NAME.replace("{{SUMMONER_NAME}}", name));
    }

    public List<Summoner> byNames() {
        return null;
    }
    public void byPuuid() {

    }
    public void bySummonerId() {

    }
}
