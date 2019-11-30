package com.onlol.fetcher.firstrun;

import com.onlol.database.model.RunLog;
import com.onlol.database.repository.RunLogRepository;
import com.onlol.fetcher.api.connector.LeaguesConnector;
import com.onlol.fetcher.ddragon.connector.*;
import com.onlol.fetcher.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/*
This class runs on the first time app inits,
so every next time, app has needed data.
 */
@Component
public class InitialSetup implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private RunLogRepository runLogRepository;

    @Autowired
    private LeaguesConnector leaguesConnector;

    @Autowired
    private LogService logger;

    @Autowired
    private Environment env;

    @Autowired
    private GameItemsConnector gameItemsConnector;

    @Autowired
    private GameInfoConnector gameInfoConnector;

    @Autowired
    private GameDataConnector gameDataConnector;

    @Autowired
    private GameChampionConnector gameChampionConnector;
    @Autowired
    private GameSummonerConnector gameSummonerConnector;

    /* Execute after app is fully loaded and setup */
    public void onApplicationEvent(ApplicationStartedEvent contextRefreshedEvent) {
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
        this.logger.info("Retriving latest versions...");
        this.gameVersionsInit();
        this.logger.info("Retriving languages...");
        this.gameLanguagesInit();
        this.logger.info("Retriving summoner spells...");
        this.gameSummonerSpellsInit();
        this.logger.info("Retriving summoner profile images...");
        this.summonerProfileImagesInit();
        this.logger.info("Retriving seasons...");
        this.seasonsInit();
        this.logger.info("Retriving base champions ...");
        this.gameChampionsInit();
        this.logger.info("Retriving latest champion rotation...");
        this.championRotationInit();
        this.logger.info("Retriving queue types...");
        this.queuesInit();
        this.logger.info("Retriving game maps...");
        this.mapsInit();
        this.logger.info("Retriving game modes...");
        this.gameModesInit();
        this.logger.info("Retriving all game items ...");
        this.itemsInit();
        this.logger.info("Retriving all regions realms...");
        this.realmsInit();
        this.logger.info("Retriving game types...");
        this.gameTypesInit();
        this.logger.info("Retriving league challenger leaders...");
        this.challengerLadderInit();
        this.logger.info("Retriving league grandmaster ladders...");
        this.grandMasterLadderInit();
        this.logger.info("Retriving league master ladders...");
        this.masterLadderInit();
        this.logger.info("Retriving LoL status...");
        this.lolStatusInit();
        this.logger.info("App first init finished!");
    }

    private void gameVersionsInit() {
        this.gameInfoConnector.versions();
    }
    private void challengerLadderInit() {
        this.leaguesConnector.challengerLadderGlobal();
    }

    private void gameLanguagesInit() {
        this.gameInfoConnector.languages();
    }

    private void gameChampionsInit() {
        if (this.env.getActiveProfiles()[0].equalsIgnoreCase("dev")) {
            this.gameChampionConnector.champions();
        } else {
            this.gameChampionConnector.championsHistorical();
        }
    }

    private void championRotationInit() {
        this.gameChampionConnector.championRotation();
    }

    private void seasonsInit() {
        this.gameInfoConnector.seasons();
    }

    private void queuesInit() {
        this.gameDataConnector.gameQueues();
    }

    private void mapsInit() {
        this.gameDataConnector.gameMaps();
    }

    private void gameModesInit() {
        this.gameDataConnector.gameModes();
    }

    private void itemsInit() {
        if (this.env.getActiveProfiles()[0].equalsIgnoreCase("dev")) {
            this.gameItemsConnector.items();
        } else {
            this.gameItemsConnector.itemsHistorical();
        }
    }

    private void realmsInit() {
        this.gameInfoConnector.realms();
    }

    private void gameTypesInit() {
        this.gameDataConnector.gameTypes();
    }
    private void masterLadderInit() {
        this.leaguesConnector.masterLadderGlobal();
    }
    private void grandMasterLadderInit() {
        this.leaguesConnector.grandMasterLadderGlobal();
    }
    private void lolStatusInit() {
        this.gameInfoConnector.statusShard();
    }

    private void gameSummonerSpellsInit() {
        this.gameSummonerConnector.summonerSpells();
    }

    private void summonerProfileImagesInit() {
        this.gameSummonerConnector.summonerProfileImages();
    }
}