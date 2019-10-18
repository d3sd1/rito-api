package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.DdragonConnector;
import com.onlol.fetcher.firstrun.RequiresInitialSetup;
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
    //TODO: Recuperar estado lol
    // GET /lol/status/v3/shard-data

    //TODO: scrappear las siguientes URL (tener en cuenta los idiomas y las verisones. se han de iterar
    // para todas las versiones solo cuando la app no este inicializando
    // y para la version actual cuando este inicializando.
    // se han de recuperar siempre para todos los idiomas.
    /*
    http://ddragon.leagueoflegends.com/cdn/9.20.1/data/en_US/summoner.json
    http://ddragon.leagueoflegends.com/cdn/9.20.1/data/en_US/profileicon.json
    save champion texts
     */
}