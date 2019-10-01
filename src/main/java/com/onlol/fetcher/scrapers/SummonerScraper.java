package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.MatchConnector;
import com.onlol.fetcher.api.connector.SummonerConnector;
import com.onlol.fetcher.api.model.Summoner;
import com.onlol.fetcher.api.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Component
public class SummonerScraper {

    @Autowired
    private SummonerConnector summonerConnector;

    @Autowired
    private MatchConnector matchConnector;

    @Autowired
    private SummonerRepository summonerRepository;
/*
    @Scheduled(fixedRate = 5000)
    public void ret() {
        Summoner summoner = this.summonerRepository.findTopByOrderByLastTimeUpdated();
        if(summoner == null) {
            System.out.println("No summoners to update...");
            return;
        }
        if(!summoner.getLastTimeUpdated().plusDays(7).isBefore(LocalDateTime.now())) {
            System.out.println(summoner.getName() + " already up to date. No summoners to update, sleeping 30s...");

            try {
                TimeUnit.SECONDS.sleep(30);
            } catch(Exception e) {

            }

            return;
        }
        System.out.println("Updating summoner " + summoner.getName());
        summoner = this.summonerConnector.bySummonerId(summoner.getId());
        System.out.println("Retrieving games of summoner " + summoner.getName());
        this.matchConnector.matchListByAccount(summoner.getAccountId());
        //TODO: guardar masteries (another endpoint)
        //TODO: revisar DB y rellenar huecos vacios con constants api etc (hay tablas que no estan al 100% rellenas)
    }

 */
}
