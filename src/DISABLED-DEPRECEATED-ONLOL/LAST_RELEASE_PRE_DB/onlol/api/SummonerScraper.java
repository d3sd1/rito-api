/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import global.api.ApiConnector;
import global.model.ApiEndpoint;
import global.model.ApiResponse;
import global.model.Platform;
import global.repository.ApiEndpointRepository;
import global.services.Logger;
import com.onlol.ScraperModel;
import com.onlol.model.Summoner;
import com.onlol.model.SummonerIdentity;
import com.onlol.repository.SummonerIdentityRepository;
import com.onlol.repository.SummonerRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * The type League exp.
 */
@Component
public class SummonerScraper extends ScraperModel<Summoner, Summoner> {
    private ApiEndpointRepository apiEndpointRepository;
    private SummonerIdentityRepository summonerIdentityRepository;
    private SummonerRepository summonerRepository;

    public SummonerScraper(Logger logger, ApiConnector apiConnector, ObjectMapper objectMapper, ApiEndpointRepository apiEndpointRepository,
                           SummonerIdentityRepository summonerIdentityRepository, SummonerRepository summonerRepository) {
        super(logger, apiConnector, objectMapper);
        this.apiEndpointRepository = apiEndpointRepository;
        this.summonerIdentityRepository = summonerIdentityRepository;
        this.summonerRepository = summonerRepository;
    }

    @Override
    protected boolean canJoin(Optional<Summoner> optional) {
        return true;
    }

    @Override
    protected Optional<Summoner> getObject() {
        return this.summonerRepository.findTopByExecutingIsFalseOrderByLastExecTimeAsc();
    }

    @Override
    protected ApiEndpoint getApiEndpoint() {
        return this.apiEndpointRepository.findByKeyName("lol-summoner-byid");
    }

    @Override
    protected void beforeApiCall(Summoner obj) {
        this.summonerRepository.save(obj);
        //this.summonerIdentityRepository.save(obj);
    }

    @Override
    protected void afterApiCall(Summoner obj, Summoner resp) {
        this.summonerRepository.save(obj);
        //this.summonerIdentityRepository.save(obj);
    }

    @Override
    protected Platform getPlatform(Summoner obj) {
        return obj.getPlatform();
    }

    @Override
    protected HashMap<String, String> getApiCallParameters(Summoner obj) {
        HashMap<String, String> params = new HashMap<>();

        List<SummonerIdentity> summonerIdentities = this.summonerIdentityRepository.findBySummoner(obj);
        SummonerIdentity summonerIdentity;
        if (!summonerIdentities.isEmpty()) {
            //TODO: elegir best api key entre summonerIdentities.apikey!! para balancear mehior la carga
            summonerIdentity = summonerIdentities.get(0);
            this.setForcedApiKey(summonerIdentity.getApiKey());
            params.put("encryptedSummonerId", summonerIdentity.getSummonerId());
        }
        return params;
    }

    @Override
    protected boolean handleApiResponse(ApiResponse apiResponse) {
        return true;
    }

    @Override
    protected HashMap<String, Object> getDeserializationInjectors(Summoner obj, ApiResponse apiResponse) {
        HashMap<String, Object> injectors = new HashMap<>();
        injectors.put("apiKey", apiResponse.getApiKey());
        injectors.put("summoner", obj);
        return injectors;
    }
}
