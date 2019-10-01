package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.DdragonConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DdragonScraper {
/*
    //TODO: que estas funciones sean async
    @Autowired
    private DdragonConnector ddragonConnector;

    @Scheduled(cron = "0 1 1 * * ?")
    @PostConstruct
    @Async
    public void gameVersions() {
        this.ddragonConnector.versions();
    }

    @Scheduled(cron = "0 1 1 * * ?")
    @PostConstruct
    @Async
    public void gameLanguages() {
        this.ddragonConnector.languages();
    }

    @Scheduled(cron = "0 1 1 * * ?")
    @PostConstruct
    @Async
    public void gameChampions() {
        this.ddragonConnector.championsHistorical();
    }
*/
}
