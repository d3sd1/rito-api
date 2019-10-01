package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.DdragonConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GameDataScraper {

    @Autowired
    private DdragonConnector ddragonConnector;


    @Scheduled(cron = "0 1 1 * * ?")
    @PostConstruct
    public void gameVersions() {
        this.ddragonConnector.versions();
    }

    @Scheduled(fixedRate = 5000)
    public void ret() {
        //TODO: game version
        //TODO: listado de campeones
    }
}
