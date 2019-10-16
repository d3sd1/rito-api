package com.onlol.fetcher.firstrun;

import com.onlol.fetcher.api.connector.DdragonConnector;
import com.onlol.fetcher.api.connector.SummonerConnector;
import com.onlol.fetcher.api.model.RunLog;
import com.onlol.fetcher.api.repository.RunLogRepository;
import com.onlol.fetcher.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/*
This class runs on the first time app inits,
so every next time, app has needed data.
 */
@Component
public class InitialSetup implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private DdragonConnector ddragonConnector;

    @Autowired
    private RunLogRepository runLogRepository;

    @Autowired
    private SummonerConnector summonerConnector;

    @Autowired
    private LogService logger;

    /* Execute after app is fully loaded and setup */
    public void onApplicationEvent(ApplicationStartedEvent contextRefreshedEvent ) {
        this.runLogger();
    }

    public void runLogger() {
        this.logger.debug("App init!");
        if (this.runLogRepository.findAll().isEmpty()) {
            this.logger.info("App running first time... Adding required data.");
            this.runFirstTime();
        }

        /* Add current execution to logs */
        this.runLogRepository.save(new RunLog());
    }

    private void runFirstTime() {
        this.logger.info("Retriving lastest versions...");
        this.gameVersionsInit();
        this.logger.info("Retriving languages...");
        this.gameLanguagesInit();
        this.logger.info("Retriving seasons...");
        this.seasonsInit();
        this.logger.info("Retriving all game champions (w/ all languages on all regions)...");
        this.gameChampionsInit();
        this.logger.info("Retriving lastest champion rotation...");
        this.championRotationInit();
        this.logger.info("Retriving queue types...");
        this.queuesInit();
        this.logger.info("Retriving game maps...");
        this.mapsInit();
        this.logger.info("Retriving game modes...");
        this.gameModesInit();
        this.logger.info("Retriving all game items (w/ all languages on all regions)...");
        this.itemsInit();
        this.logger.info("Retriving all regions realms...");
        this.realmsInit();
        this.logger.info("Retriving game types...");
        this.gameTypesInit();
        this.logger.info("Retriving league leaders...");
        this.gameTypesInit(); // TODO: recuperar aqui a los top de cada region para empezar a scrapear
    }

    private void gameVersionsInit() {
        this.ddragonConnector.versions();
    }

    private void gameLanguagesInit() {
        this.ddragonConnector.languages();
    }

    private void gameChampionsInit() {
        this.ddragonConnector.championsHistorical();
    }

    private void championRotationInit() {
        this.ddragonConnector.championRotation();
    }

    private void seasonsInit() {
        this.ddragonConnector.seasons();
    }

    private void queuesInit() {
        this.ddragonConnector.queues();
    }

    private void mapsInit() {
        this.ddragonConnector.maps();
    }

    private void gameModesInit() {
        this.ddragonConnector.gameModes();
    }

    private void itemsInit() {
        this.ddragonConnector.itemsHistorical();
    }

    private void realmsInit() {
        this.ddragonConnector.realms();
    }

    private void gameTypesInit() {
        this.ddragonConnector.gameTypes();
    }
}

//TODO: decomentar esto cuando el endpoint quite la trailing comma