package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.DdragonConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
@EnableAsync
public class DdragonScraper {

    @Autowired
    private DdragonConnector ddragonConnector;

    @Async
    @Scheduled(cron = "0 1 1 * * ?")
    @PostConstruct
    public void gameVersions() {
        this.ddragonConnector.versions();
    }

    @Async
    @Scheduled(cron = "0 1 1 * * ?")
    @PostConstruct
    public void gameLanguages() {
        this.ddragonConnector.languages();
    }

    @Async
    @PostConstruct
    public void gameChampions() { // Used for lower init time
        try {
            this.ddragonConnector.champions();
        } catch(Exception e) {

        }
    }
    @Async
    @Scheduled(cron = "0 1 1 * * ?")
    public void gameChampionsHistorical() { // Used for retrieving all data
        try {
            this.ddragonConnector.championsHistorical();
        } catch(Exception e) {

        }
    }


    @Async
    @PostConstruct
    public void championRotation() { // Used for lower init time
        try {
            this.ddragonConnector.championRotation();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Async
    @Scheduled(cron = "0 1 1 * * ?")
    public void championRotationCron() { // Used for retrieving all data
        try {
            this.ddragonConnector.championRotation();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @Async
    @PostConstruct
    public void seasonsInit() { // Used for lower init time
        try {
            this.ddragonConnector.seasons();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Async
    @Scheduled(cron = "0 1 1 * * ?")
    public void seasonsCron() { // Used for retrieving all data
        try {
            this.ddragonConnector.seasons();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @PostConstruct
    public void queuesInit() { // Used for lower init time
        try {
            this.ddragonConnector.queues();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Async
    @Scheduled(cron = "0 1 1 * * ?")
    public void queuesCron() { // Used for retrieving all data
        try {
            this.ddragonConnector.queues();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Async
    @PostConstruct
    public void mapsInit() { // Used for lower init time
        try {
            this.ddragonConnector.maps();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Async
    @Scheduled(cron = "0 1 1 * * ?")
    public void mapsCron() { // Used for retrieving all data
        try {
            this.ddragonConnector.maps();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @Async
    @PostConstruct
    public void gameModesInit() { // Used for lower init time
        try {
            this.ddragonConnector.gameModes();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Async
    @Scheduled(cron = "0 1 1 * * ?")
    public void gameModesQueue() { // Used for retrieving all data
        try {
            this.ddragonConnector.gameModes();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @PostConstruct
    public void gameTypesInit() { // Used for lower init time
        try {
            //TODO: decomentar esto cuando el endpoint quite la trailing comma
            //this.ddragonConnector.gameTypes();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Async
    @Scheduled(cron = "0 1 1 * * ?")
    public void gameTypesQueue() { // Used for retrieving all data
        try {
            //TODO: decomentar esto cuando el endpoint quite la trailing comma
            //this.ddragonConnector.gameTypes();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: Recuperar estado lol
    // GET /lol/status/v3/shard-data
}
