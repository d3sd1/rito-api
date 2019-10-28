package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.ddragon.connector.*;
import com.onlol.fetcher.firstrun.RequiresInitialSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
@EnableAsync
public class DdragonScraper {

    @Autowired
    private GameDataConnector gameDataConnector;

    @Autowired
    private GameInfoConnector gameInfoConnector;

    @Autowired
    private GameChampionConnector gameChampionConnector;

    @Autowired
    private GameItemsConnector gameItemsConnector;

    @Autowired
    private GameSummonerConnector gameSummonerConnector;

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void gameVersionsCron() {
        this.gameInfoConnector.versions();
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void gameLanguagesCron() {
        this.gameInfoConnector.languages();
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void gameChampionsCron() { // Used for retrieving all data
        try {
            this.gameChampionConnector.championsHistorical();
        } catch(Exception e) {

        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void championRotationCron() { // Used for retrieving all data
        try {
            this.gameChampionConnector.championRotation();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void seasonsCron() { // Used for retrieving all data
        try {
            this.gameInfoConnector.seasons();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void queuesCron() { // Used for retrieving all data
        try {
            this.gameDataConnector.gameQueues();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void mapsCron() { // Used for retrieving all data
        try {
            this.gameDataConnector.gameMaps();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void gameModesQueue() { // Used for retrieving all data
        try {
            this.gameDataConnector.gameModes();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void gameTypesQueue() { // Used for retrieving all data
        try {
            this.gameDataConnector.gameTypes();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void realmsCron() { // Used for retrieving all data
        try {
            this.gameInfoConnector.realms();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void itemsCron() { // Used for retrieving all data
        try {
            this.gameItemsConnector.itemsHistorical();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void lolStatus() {
        try {
            this.gameInfoConnector.statusShard();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void summonerSpells() {
        try {
            this.gameSummonerConnector.summonerSpellsHistorical();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void summonerProfileImages() {
        try {
            this.gameSummonerConnector.summonerProfileImagesHistorical();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}