/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.scraper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.global.api.ApiConnector;
import com.global.model.ApiCall;
import com.global.model.ApiResponse;
import com.global.model.Platform;
import com.global.repository.ApiEndpointRepository;
import com.global.repository.RiotGameRepository;
import com.global.services.Logger;
import com.onlol.model.LeagueDivision;
import com.onlol.model.LeagueEntry;
import com.onlol.model.LeagueTier;
import com.onlol.model.Queue;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class LeagueExpScraper implements Runnable {
    private ApiEndpointRepository apiEndpointRepository;
    private ObjectMapper objectMapper;
    private Logger logger;
    private ApiConnector apiConnector;
    private RiotGameRepository riotGameRepository;
    private Platform platform;
    private Queue queue;
    private LeagueTier leagueTier;
    private LeagueDivision leagueDivision;
    private boolean appClosing;

    public LeagueExpScraper(ApiEndpointRepository apiEndpointRepository, ObjectMapper objectMapper, Logger logger, ApiConnector apiConnector, RiotGameRepository riotGameRepository) {
        this.apiEndpointRepository = apiEndpointRepository;
        this.objectMapper = objectMapper;
        this.logger = logger;
        this.apiConnector = apiConnector;
        this.riotGameRepository = riotGameRepository;
        this.appClosing = false;
    }


    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public void setLeagueTier(LeagueTier leagueTier) {
        this.leagueTier = leagueTier;
    }

    public void setLeagueDivision(LeagueDivision leagueDivision) {
        this.leagueDivision = leagueDivision;
    }

    @Override
    public void run() {
        int currentPage = 1;
        boolean morePages = true;
        while (morePages) {
            if (this.appClosing) {
                this.logger.info("Async scraper forced to end due to app shutdown.");
                morePages = false;
                continue;
            }
            this.logger.info(String.format("Executing league scraper (%s) for page %s of queue %s and tier %s and division %s",
                    platform.getServiceRegion(), currentPage, queue.getKeyName(), leagueTier.getKeyName(), leagueDivision.getKeyName()));
            ApiCall apiCall = new ApiCall();
            apiCall.setApiEndpoint(this.apiEndpointRepository.findByKeyName("lol-leagues-scraper"));
            apiCall.setRiotGame(this.riotGameRepository.findByGameName("LoL"));
            apiCall.setPlatform(platform);
            apiCall.getParameters().put("queue", queue.getKeyName());
            apiCall.getParameters().put("tier", leagueTier.getKeyName());
            apiCall.getParameters().put("division", leagueDivision.getKeyName());
            apiCall.getParameters().put("page", String.valueOf(currentPage));
            try {
                ApiResponse apiResponse = this.apiConnector.get(apiCall);
                // 400 err - prevent infinite loop
                if (apiResponse.getResponseCode() == 400) {
                    morePages = false;
                    continue;
                }
                // Riot api struggling... Wait a bit, so we get no deserialization errors.
                if (apiResponse.getResponseCode() == 503) {
                    Thread.sleep(2000);
                    continue;
                }
                InjectableValues.Std injectable = new InjectableValues.Std();
                injectable.addValue("apiKey", apiResponse.getApiKey())
                        .addValue("platform", apiCall.getPlatform())
                        .addValue("leagueDivision", leagueDivision)
                        .addValue("queue", queue)
                        .addValue("leagueTier", leagueTier);
                objectMapper.setInjectableValues(injectable);
                final List<LeagueEntry> entries = objectMapper.readValue(apiResponse.getJson(), new TypeReference<List<LeagueEntry>>() {
                });

                // no more data - prevent infinite loop
                if (entries.size() == 0) {
                    morePages = false;
                    continue;
                }
                currentPage++;
            } catch (Exception e) {
                e.printStackTrace();
                this.logger.error(e);
            }
        }

    }

}