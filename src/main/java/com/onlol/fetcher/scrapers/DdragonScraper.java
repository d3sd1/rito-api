package com.onlol.fetcher.scrapers;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
@Component
@EnableAsync
public class DdragonScraper {
/*
    @Autowired
    private DdragonConnector ddragonConnector;

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void gameVersionsCron() {
        this.ddragonConnector.versions();
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void gameLanguagesCron() {
        this.ddragonConnector.languages();
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void gameChampionsCron() { // Used for retrieving all data
        try {
            this.ddragonConnector.championsHistorical();
        } catch(Exception e) {

        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void championRotationCron() { // Used for retrieving all data
        try {
            this.ddragonConnector.championRotation();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void seasonsCron() { // Used for retrieving all data
        try {
            this.ddragonConnector.seasons();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void queuesCron() { // Used for retrieving all data
        try {
            this.ddragonConnector.queues();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void mapsCron() { // Used for retrieving all data
        try {
            this.ddragonConnector.maps();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void gameModesQueue() { // Used for retrieving all data
        try {
            this.ddragonConnector.gameModes();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void gameTypesQueue() { // Used for retrieving all data
        try {
            this.ddragonConnector.gameTypes();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void realmsCron() { // Used for retrieving all data
        try {
            this.ddragonConnector.realms();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void itemsCron() { // Used for retrieving all data
        try {
            this.ddragonConnector.itemsHistorical();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void lolStatus() {
        try {
            this.ddragonConnector.lolStatus();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void summonerSpells() {
        try {
            this.ddragonConnector.summonerSpellsHistorical();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void summonerProfileImages() {
        try {
            this.ddragonConnector.summonerProfileImagesHistorical();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}