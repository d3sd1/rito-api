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
import global.repository.PlatformRepository;
import global.repository.RiotGameRepository;
import global.services.Logger;
import com.onlol.ScraperModel;
import com.onlol.model.*;
import com.onlol.model.LeagueEntry;
import com.onlol.model.LeagueExp;
import com.onlol.repository.LeagueExpRepository;
import com.onlol.repository.LeagueTierDivisionRepository;
import com.onlol.repository.QueueRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * The type League exp.
 */
@Component
public class LeagueExpScraper extends ScraperModel<LeagueExp, List<LeagueEntry>> {
    private PlatformRepository platformRepository;
    private LeagueTierDivisionRepository leagueTierDivisionRepository;
    private QueueRepository queueRepository;
    private ApplicationContext applicationContext;
    private ApiEndpointRepository apiEndpointRepository;
    private ObjectMapper objectMapper;
    private Logger logger;
    private ApiConnector apiConnector;
    private RiotGameRepository riotGameRepository;
    private LeagueExpRepository leagueExpRepository;

    /**
     * Instantiates a new League exp.
     *
     * @param platformRepository           the platform repository
     * @param leagueTierDivisionRepository the league tier division repository
     * @param queueRepository              the queue repository
     * @param applicationContext           the application context
     */
    public LeagueExpScraper(PlatformRepository platformRepository,
                            LeagueTierDivisionRepository leagueTierDivisionRepository,
                            QueueRepository queueRepository,
                            ApplicationContext applicationContext, ApiEndpointRepository apiEndpointRepository, ObjectMapper objectMapper, Logger logger, ApiConnector apiConnector, RiotGameRepository riotGameRepository,
                            LeagueExpRepository leagueExpRepository) {
        super(logger, apiConnector, objectMapper);
        this.platformRepository = platformRepository;
        this.leagueTierDivisionRepository = leagueTierDivisionRepository;
        this.queueRepository = queueRepository;
        this.applicationContext = applicationContext;
        this.apiEndpointRepository = apiEndpointRepository;
        this.objectMapper = objectMapper;
        this.logger = logger;
        this.apiConnector = apiConnector;
        this.riotGameRepository = riotGameRepository;
        this.leagueExpRepository = leagueExpRepository;
    }

    @Override
    protected boolean handleApiResponse(ApiResponse apiResponse) {
        // 400 err - prevent infinite loop
        if (apiResponse.getResponseCode() == 400) {
            return false;
        }
        // Riot api struggling... Wait a bit, so we get no deserialization errors.
        if (apiResponse.getResponseCode() == 503) {
            // Do nothing, just iterate again.
            return false;
        }
        return true;
    }

    @Override
    protected HashMap<String, Object> getDeserializationInjectors(LeagueExp leagueExp, ApiResponse apiResponse) {

        HashMap<String, Object> injectors = new HashMap<>();
        injectors.put("apiKey", apiResponse.getApiKey());
        injectors.put("platform", leagueExp.getPlatform());
        injectors.put("leagueDivision", leagueExp.getLeagueTierDivision().getLeagueDivision());
        injectors.put("queue", leagueExp.getQueue());
        injectors.put("leagueTier", leagueExp.getLeagueTierDivision().getLeagueTier());
        return injectors;
    }

    @Override
    protected Optional<LeagueExp> getObject() {
        return this.leagueExpRepository.findTopByExecutingIsFalseOrderByLastExecTimeAsc();
    }

    @Override
    protected ApiEndpoint getApiEndpoint() {
        return this.apiEndpointRepository.findByKeyName("lol-leagues-scraper");
    }

    @Override
    protected Platform getPlatform(LeagueExp leagueExp) {
        return leagueExp.getPlatform();
    }

    @Override
    protected HashMap<String, String> getApiCallParameters(LeagueExp leagueExp) {
        HashMap<String, String> params = new HashMap<>();
        params.put("queue", leagueExp.getQueue().getKeyName());
        params.put("tier", leagueExp.getLeagueTierDivision().getLeagueTier().getKeyName());
        params.put("division", leagueExp.getLeagueTierDivision().getLeagueDivision().getKeyName());
        params.put("page", String.valueOf(leagueExp.getLastPage()));
        return params;
    }

    @Override
    public boolean canJoin(Optional<LeagueExp> leagueExpOpt) {
        if (!leagueExpOpt.isPresent()) {
            this.logger.info("No league exp found.");
            return false;
        }
        return true;
    }

    @Override
    protected void beforeApiCall(LeagueExp leagueExp) {
    }

    @Override
    protected void afterApiCall(LeagueExp leagueExp, List<LeagueEntry> leagueEntries) {
        // no more data - prevent infinite loop
        if (leagueEntries.size() == 0) {
            leagueExp.setLastPage(1);
            leagueExp.setLastExecTime(System.currentTimeMillis() / 1000);
        } else {
            leagueExp.setLastPage(leagueExp.getLastPage() + 1);
            leagueExp.setLastExecTime(0L);
        }
        this.leagueExpRepository.save(leagueExp);
    }
}
