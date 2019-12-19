/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.configuration;

import com.global.model.*;
import com.global.repository.*;
import com.global.services.Logger;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Setup the project for new servers, to load required data from API before calling requests.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Component
public class InitialSetup implements ApplicationListener<ApplicationStartedEvent> {

    private RunLogRepository runLogRepository;
    private Logger logger;
    private Environment env;
    private ApiKeyRepository apiKeyRepository;
    private PlatformRepository platformRepository;
    private RiotGameRepository riotGameRepository;
    private ApiKeyRateLimitsRepository apiKeyRateLimitsRepository;

    /**
     * Take beans to initialize setup dependencies.
     *
     * @param runLogRepository           the run log repository
     * @param logger                     the logger
     * @param env                        the env
     * @param apiKeyRepository           the api key repository
     * @param platformRepository         the platform repository
     * @param riotGameRepository         the riot game repository
     * @param apiKeyRateLimitsRepository the api key rate limits repository
     * @author d3sd1
     */
    public InitialSetup(RunLogRepository runLogRepository,
                        Logger logger,
                        Environment env,
                        ApiKeyRepository apiKeyRepository,
                        PlatformRepository platformRepository,
                        RiotGameRepository riotGameRepository,
                        ApiKeyRateLimitsRepository apiKeyRateLimitsRepository) {
        this.runLogRepository = runLogRepository;
        this.logger = logger;
        this.env = env;
        this.apiKeyRepository = apiKeyRepository;
        this.platformRepository = platformRepository;
        this.riotGameRepository = riotGameRepository;
        this.apiKeyRateLimitsRepository = apiKeyRateLimitsRepository;
    }

    /* Execute after app is fully loaded and setup */
    public void onApplicationEvent(ApplicationStartedEvent contextRefreshedEvent) {
        this.logLoad();
    }

    /**
     * Log the current load to database.
     *
     * @author d3sd1
     */
    public void logLoad() {
        this.logger.debug("App init!");
        if (this.runLogRepository.findAll().isEmpty()) {
            this.logger.info("App running first time... Adding required data.");
            this.runFirstTime();
            this.logger.info("App first init finished!");
        }

        this.runLogRepository.save(new RunLog());
    }

    /**
     * Configure the required dependencies.
     *
     * @author d3sd1
     */
    private void runFirstTime() {
        this.logger.info("Configuring api keys...");
        this.configureApiKeys();
    }

    /**
     * Configure API keys.
     *
     * @author d3sd1
     */
    private void configureApiKeys() {
        List<Platform> platforms = this.platformRepository.findAll();
        List<RiotGame> riotGames = this.riotGameRepository.findAll();
        for (ApiKey apiKey : this.apiKeyRepository.findAll()) {
            if (apiKey.isConfigured()) {
                continue;
            }
            apiKey.setConfigured(true);
            apiKey.setDisabled(false);
            apiKey.setApiKeyRateLimits(this.apiKeyRateLimitsRepository.save(new ApiKeyRateLimits()));

            // By default key is enabled for all regions and platforms.
            for (RiotGame riotGame : riotGames) {
                for (Platform platform : platforms) {
                    ApiKeyAvailability availability = new ApiKeyAvailability();
                    availability.setPlatform(platform);
                    availability.setRiotGame(riotGame);
                    apiKey.getAvailability().add(availability);
                }
            }
            this.apiKeyRepository.save(apiKey);
        }
    }
}