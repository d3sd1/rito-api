/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.configuration;

import global.model.Platform;
import global.repository.ApiKeyRateLimitsRepository;
import global.repository.ApiKeyRepository;
import global.repository.PlatformRepository;
import global.repository.RiotGameRepository;
import global.repository.RunLogRepository;
import global.services.Logger;
import com.onlol.model.LeagueExp;
import com.onlol.model.LeagueTierDivision;
import com.onlol.model.Queue;
import com.onlol.repository.LeagueExpRepository;
import com.onlol.repository.LeagueTierDivisionRepository;
import com.onlol.repository.QueueRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Setup the project for new servers, to load required data from API before calling requests.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Service
public class ConfigureOnLoL {

    private RunLogRepository runLogRepository;
    private Logger logger;
    private Environment env;
    private ApiKeyRepository apiKeyRepository;
    private PlatformRepository platformRepository;
    private RiotGameRepository riotGameRepository;
    private ApiKeyRateLimitsRepository apiKeyRateLimitsRepository;
    private QueueRepository queueRepository;
    private LeagueTierDivisionRepository leagueTierDivisionRepository;
    private LeagueExpRepository leagueExpRepository;

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
    public ConfigureOnLoL(RunLogRepository runLogRepository,
                          Logger logger,
                          Environment env,
                          ApiKeyRepository apiKeyRepository,
                          PlatformRepository platformRepository,
                          RiotGameRepository riotGameRepository,
                          ApiKeyRateLimitsRepository apiKeyRateLimitsRepository,
                          QueueRepository queueRepository,
                          LeagueTierDivisionRepository leagueTierDivisionRepository,
                          LeagueExpRepository leagueExpRepository) {
        this.runLogRepository = runLogRepository;
        this.logger = logger;
        this.env = env;
        this.apiKeyRepository = apiKeyRepository;
        this.platformRepository = platformRepository;
        this.riotGameRepository = riotGameRepository;
        this.apiKeyRateLimitsRepository = apiKeyRateLimitsRepository;
        this.queueRepository = queueRepository;
        this.leagueTierDivisionRepository = leagueTierDivisionRepository;
        this.leagueExpRepository = leagueExpRepository;
    }

    /**
     * Configure the required dependencies.
     *
     * @author d3sd1
     */
    public void configure() {
        this.logger.info("Configuring api keys...");
        this.configureLeagueExpScraper();
    }

    private void configureLeagueExpScraper() {

        for (Platform platform : this.platformRepository.findAll()) {
            for (Queue queue : this.queueRepository.findAll()) {
                for (LeagueTierDivision leagueTierDivision : this.leagueTierDivisionRepository.findAll()) {
                    LeagueExp leagueExp = new LeagueExp();
                    leagueExp.setPlatform(platform);
                    leagueExp.setQueue(queue);
                    leagueExp.setLeagueTierDivision(leagueTierDivision);
                    leagueExp.setLastPage(1);
                    this.leagueExpRepository.save(leagueExp);
                }
            }
        }
    }

}