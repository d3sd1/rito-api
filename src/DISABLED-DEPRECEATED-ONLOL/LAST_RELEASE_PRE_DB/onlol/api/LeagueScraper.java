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
import com.onlol.model.League;
import com.onlol.repository.LeagueRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
public class LeagueScraper extends ScraperModel<League, League> {
    private LeagueRepository leagueRepository;
    private ApiEndpointRepository apiEndpointRepository;
    public LeagueScraper(Logger logger, ApiConnector apiConnector, ObjectMapper objectMapper, LeagueRepository leagueRepository,
                         ApiEndpointRepository apiEndpointRepository) {
        super(logger, apiConnector, objectMapper);
        this.leagueRepository = leagueRepository;
        this.apiEndpointRepository = apiEndpointRepository;
    }

    @Override
    protected boolean canJoin(Optional<League> optional) {
        return optional.isPresent(); //TODO: revisar fechas?
    }

    @Override
    protected Optional<League> getObject() {
        return this.leagueRepository.findTopByExecutingIsFalseOrderByLastExecTimeAsc();
    }

    @Override
    protected ApiEndpoint getApiEndpoint() {
        return this.apiEndpointRepository.findByKeyName("lol-leagues-byid");
    }

    @Override
    protected void beforeApiCall(League obj) {
        this.leagueRepository.save(obj);
    }

    @Override
    protected void afterApiCall(League obj, League resp) {
        this.leagueRepository.save(obj);
    }

    @Override
    protected Platform getPlatform(League obj) {
        return obj.getPlatform();
    }

    @Override
    protected HashMap<String, String> getApiCallParameters(League obj) {

        HashMap<String, String> params = new HashMap<>();
        params.put("leagueId", obj.getRiotId());
        return params;
    }

    @Override
    protected boolean handleApiResponse(ApiResponse apiResponse) {
        return true; // Nothing to check after api resp
    }

    @Override
    protected HashMap<String, Object> getDeserializationInjectors(League obj, ApiResponse apiResponse) {
        HashMap<String, Object> injectors = new HashMap<>();
        injectors.put("apiKey", apiResponse.getApiKey());
        injectors.put("league", obj);
        return injectors;
    }
}
