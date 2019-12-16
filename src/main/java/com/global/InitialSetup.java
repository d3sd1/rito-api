package com.global;

import com.global.model.*;
import com.global.repository.*;
import com.global.services.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

/*
This class runs on the first time app inits,
so every next time, app has needed data.
 */
@Component
public class InitialSetup implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private RunLogRepository runLogRepository;

    @Autowired
    private Logger logger;

    @Autowired
    private Environment env;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private RiotGameRepository riotGameRepository;

    @Autowired
    private ApiKeyRateLimitsRepository apiKeyRateLimitsRepository;

    /* Execute after app is fully loaded and setup */
    public void onApplicationEvent(ApplicationStartedEvent contextRefreshedEvent) {
        this.runLogger();
    }

    public void runLogger() {
        this.logger.debug("App init!");
        if (this.runLogRepository.findAll().isEmpty()) {
            this.logger.info("App running first time... Adding required data.");
            this.runFirstTime();
            this.logger.info("App first init finished!");
        }

        /* Add current execution to logs */
        this.runLogRepository.save(new RunLog());
    }

    private void runFirstTime() {
        this.logger.info("Configuring api keys...");
        this.configureApiKeys();
    }

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
            //TODO: handle disable api key per game?
            //TODO: handle disable api key per platform?

            apiKey.setRiotGames(riotGames);
            // By default every api key is enabled for all platforms.
            apiKey.setPlatforms(platforms);
            this.apiKeyRepository.save(apiKey);
        }
    }
}