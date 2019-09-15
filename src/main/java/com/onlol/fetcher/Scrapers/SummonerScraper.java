package com.onlol.fetcher.Scrapers;

import com.onlol.fetcher.api.connector.SummonerConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.Summoner;
import com.onlol.fetcher.api.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SummonerScraper {

    @Autowired
    private SummonerConnector summonerConnector;

    @Autowired
    private SummonerRepository summonerRepository;

    @Scheduled(fixedRate = 5000)
    public void ret() {

        System.out.println(this.summonerConnector.byName("NOVA Desdi"));
        //this.summonerRepository.save();
    }
}
