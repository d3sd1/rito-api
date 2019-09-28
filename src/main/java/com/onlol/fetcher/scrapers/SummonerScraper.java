package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.MatchConnector;
import com.onlol.fetcher.api.connector.SummonerConnector;
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
        System.out.println(this.summonerConnector.byName("NOVA Desdi"));
        System.out.println(this.summonerConnector.bySummonerId("lC8aYAAFY5malS0oqimD1c7oTDX8GlsH0aHNsmvClFAHQnM"));
        System.out.println(this.summonerConnector.byPuuid("_3H4Uimwl1Ox1s-po5RsIrY49-0TjZrcY4XEOj3Plmq3A4U6miY8priQUtqEnIWezDFI_G1vrUwqYQ"));
        System.out.println(this.summonerConnector.byAccount("XHuGvAypHqg760gTgF5DVQXbPY6g1Bv38xATlBHt9cTh4M8"));
        System.out.println(this.matchConnector.matchListByAccount("XHuGvAypHqg760gTgF5DVQXbPY6g1Bv38xATlBHt9cTh4M8"));
        //this.summonerRepository.save();
    }
}
