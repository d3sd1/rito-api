/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.api;

import com.global.model.Platform;
import com.global.repository.PlatformRepository;
import com.global.setup.RequiresInitialSetup;
import com.onlol.model.LeagueTierDivision;
import com.onlol.model.Queue;
import com.onlol.repository.LeagueTierDivisionRepository;
import com.onlol.repository.QueueRepository;
import com.onlol.scraper.LeagueExpScraper;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * The type League exp.
 */
@Component
public class LeagueExp implements ApplicationListener<ApplicationStartedEvent> {
    private PlatformRepository platformRepository;
    private LeagueTierDivisionRepository leagueTierDivisionRepository;
    private QueueRepository queueRepository;
    private ApplicationContext applicationContext;


    /**
     * Instantiates a new League exp.
     *
     * @param platformRepository           the platform repository
     * @param leagueTierDivisionRepository the league tier division repository
     * @param queueRepository              the queue repository
     * @param applicationContext           the application context
     */
    public LeagueExp(PlatformRepository platformRepository,
                     LeagueTierDivisionRepository leagueTierDivisionRepository,
                     QueueRepository queueRepository,
                     ApplicationContext applicationContext) {
        this.platformRepository = platformRepository;
        this.leagueTierDivisionRepository = leagueTierDivisionRepository;
        this.queueRepository = queueRepository;
        this.applicationContext = applicationContext;
    }

    /**
     * Run every month at 01:01 of day and after app load.
     */
    public void onApplicationEvent(ApplicationStartedEvent contextRefreshedEvent) {
        this.scraper();
    }

    /**
     * Scraper.
     */
    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 0 0 1 1/1 *")
    public void scraper() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) this.applicationContext.getBean("taskExecutor");

        for (Platform platform : this.platformRepository.findAll()) {
            for (Queue queue : this.queueRepository.findAll()) {
                for (LeagueTierDivision leagueTierDivision : this.leagueTierDivisionRepository.findAll()) {
                    LeagueExpScraper leagueExpScraper = (LeagueExpScraper) this.applicationContext.getBean("leagueExpScraper");
                    leagueExpScraper.setPlatform(platform);
                    leagueExpScraper.setQueue(queue);
                    leagueExpScraper.setLeagueTier(leagueTierDivision.getLeagueTier());
                    leagueExpScraper.setLeagueDivision(leagueTierDivision.getLeagueDivision());
                    taskExecutor.execute(leagueExpScraper);
                }
            }
        }
    }
}
