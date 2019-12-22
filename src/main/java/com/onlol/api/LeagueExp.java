/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.global.api.ApiConnector;
import com.global.model.ApiCall;
import com.global.model.ApiResponse;
import com.global.model.Platform;
import com.global.repository.ApiEndpointRepository;
import com.global.repository.PlatformRepository;
import com.global.repository.RiotGameRepository;
import com.global.services.Logger;
import com.global.setup.RequiresInitialSetup;
import com.onlol.model.LeagueDivision;
import com.onlol.model.LeagueEntry;
import com.onlol.model.LeagueTier;
import com.onlol.model.Queue;
import com.onlol.repository.LeagueDivisionRepository;
import com.onlol.repository.LeagueTierRepository;
import com.onlol.repository.QueueRepository;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LeagueExp implements ApplicationListener<ApplicationStartedEvent> {
    private ApiConnector apiConnector;
    private RiotGameRepository riotGameRepository;
    private PlatformRepository platformRepository;
    private ApiEndpointRepository apiEndpointRepository;
    private ObjectMapper objectMapper;
    private Logger logger;
    private LeagueTierRepository leagueTierRepository;
    private LeagueDivisionRepository leagueDivisionRepository;
    private QueueRepository queueRepository;

    public LeagueExp(ApiConnector apiConnector,
                     RiotGameRepository riotGameRepository,
                     PlatformRepository platformRepository,
                     ApiEndpointRepository apiEndpointRepository,
                     ObjectMapper objectMapper,
                     Logger logger,
                     LeagueTierRepository leagueTierRepository,
                     LeagueDivisionRepository leagueDivisionRepository,
                     QueueRepository queueRepository) {
        this.apiConnector = apiConnector;
        this.riotGameRepository = riotGameRepository;
        this.platformRepository = platformRepository;
        this.apiEndpointRepository = apiEndpointRepository;
        this.objectMapper = objectMapper;
        this.logger = logger;
        this.leagueTierRepository = leagueTierRepository;
        this.leagueDivisionRepository = leagueDivisionRepository;
        this.queueRepository = queueRepository;
    }

    /**
     * Run every day at 01:01 and after app load.
     */
    public void onApplicationEvent(ApplicationStartedEvent contextRefreshedEvent) {
        this.scraper();
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void scraper() {
        for (Platform platform : this.platformRepository.findAll()) {
            for (Queue queue : this.queueRepository.findAll()) {
                for (LeagueTier leagueTier : this.leagueTierRepository.findAll()) {
                    for (LeagueDivision leagueDivision : this.leagueDivisionRepository.findAll()) {
                        int currentPage = 1;
                        boolean morePages = true;
                        while (morePages) {
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
            }
        }
    }
}
