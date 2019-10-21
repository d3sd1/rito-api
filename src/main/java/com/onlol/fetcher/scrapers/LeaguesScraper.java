package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.LeaguesConnector;
import com.onlol.fetcher.api.repository.SummonerRepository;
import com.onlol.fetcher.firstrun.RequiresInitialSetup;
import com.onlol.fetcher.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class LeaguesScraper implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private LeaguesConnector leaguesConnector;

    @Autowired
    private LogService logger;

    @Autowired
    private SummonerRepository summonerRepository;

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void getChallengers() {
        this.leaguesConnector.challengerLadderGlobal();
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void getMasters() {
        this.leaguesConnector.masterLadderGlobal();
    }


    @Async
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    public void getGrandMasters() {
        this.leaguesConnector.grandMasterLadderGlobal();
    }


    public void onApplicationEvent(ApplicationStartedEvent contextRefreshedEvent) {
        this.getFeaturedGames();
    }

    // Since featured games got refresh interval, let function handle it.
    public void getFeaturedGames() {
        this.getFeaturedGames(0);
    }

    public void getFeaturedGames(Integer delay) {
        //TODO: reecuperar featured games
        // GET /lol/spectator/v4/featured-games
/*
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                List<FeaturedGame> featuredGames = this.leaguesConnector.featuredGames();
            }
            catch (Exception e){
                e.printStackTrace();
                this.logger.warning("Could not sleep on featured games: " + e.getMessage());
            }
        }).start();*/
    }


}
