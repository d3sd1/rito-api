package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.MatchConnector;
import com.onlol.fetcher.api.connector.SummonerConnector;
import com.onlol.fetcher.api.model.Summoner;
import com.onlol.fetcher.api.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SummonerScraper {

    @Autowired
    private SummonerConnector summonerConnector;

    @Autowired
    private MatchConnector matchConnector;

    @Autowired
    private SummonerRepository summonerRepository;

    @Scheduled(fixedRate = 5000)
    public void ret() {
        Summoner summoner = this.summonerRepository.findTopByOrderByLastTimeUpdated();
        if(summoner == null) {
            System.out.println("No summoners to update...");
            return;
        }
        System.out.println("Updating summoner " + summoner.getName());
        summoner = this.summonerConnector.bySummonerId(summoner.getId());
        System.out.println("Retrieving games of summoner " + summoner.getName());
        this.matchConnector.matchListByAccount(summoner.getAccountId());
        //TODO: guardar masteries (another endpoint)
    }
}
