package com.onlol.fetcher.scrapers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.connector.MatchConnector;
import com.onlol.fetcher.api.connector.SummonerConnector;
import com.onlol.fetcher.firstrun.RequiresInitialSetup;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.model.SummonerToken;
import com.onlol.fetcher.repository.RegionRepository;
import com.onlol.fetcher.repository.SummonerRepository;
import com.onlol.fetcher.repository.SummonerTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@EnableAsync
public class SummonerScraper {

    @Autowired
    private SummonerConnector summonerConnector;

    @Autowired
    private MatchConnector matchConnector;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private SummonerTokenRepository summonerTokenRepository;

    @Autowired
    private LogService logger;


    @Autowired
    private JavaMailSender javaMailSender;


    @Autowired
    private RegionRepository regionRepository;


    @PostConstruct
    @RequiresInitialSetup
    public void cleanOrphanSummoners() {
        this.logger.info("Limpiando summoners huérfanos...");
        List<Summoner> orphanSummoners = this.summonerRepository.findAllByRetrievingIsTrue();
        for (Summoner orphanSummoner : orphanSummoners) {
            orphanSummoner.setRetrieving(false);
            this.summonerRepository.save(orphanSummoner);
        }
    }

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private ObjectMapper jacksonMapper;

    @Async
    @RequiresInitialSetup
    @Scheduled(fixedRate = 5000, initialDelay = 1000)
    public void getSummonerInfo() {

        Summoner summoner = this.summonerRepository.findTopByRetrievingIsFalseOrderByLastTimeUpdated();
        if (summoner == null) {
            this.logger.info("No summoners to update.");
            return;
        }
        if (!summoner.getLastTimeUpdated().plusDays(7).isBefore(LocalDateTime.now())) {
            this.logger.info(summoner.getName() + " already up to date. No summoners to update, sleeping 30s...");

            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (Exception e) {

            }

            return;
        }
        this.logger.info("Updating summoner " + summoner.getName());
        SummonerToken summonerToken = this.summonerConnector.updateSummoner(summoner);
    }


}
