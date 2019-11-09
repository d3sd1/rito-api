package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.LeaguesConnector;
import com.onlol.fetcher.firstrun.RequiresInitialSetup;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.FeaturedGameInterval;
import com.onlol.fetcher.model.League;
import com.onlol.fetcher.model.Region;
import com.onlol.fetcher.repository.LeagueRepository;
import com.onlol.fetcher.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableAsync
public class LeaguesScraper implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private LeaguesConnector leaguesConnector;

    @Autowired
    private LogService logger;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    private boolean noLeaguesMessageShown = false;

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
        for (Region region : this.regionRepository.findAll()) {
            this.getFeaturedGames(region, 0);
        }
    }

    public void getFeaturedGames(Region region, Integer delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay * 1000); // * 1000 since we have seconds
                FeaturedGameInterval featuredGameInterval = this.leaguesConnector.featuredGames(region);
                /* If endpoint down... Sleep 5min */
                Integer clientRefreshInterval = 300;
                if (featuredGameInterval != null && featuredGameInterval.getClientRefreshInterval() != 0) {
                    clientRefreshInterval = featuredGameInterval.getClientRefreshInterval();
                }
                this.getFeaturedGames(region, clientRefreshInterval);
            } catch (Exception e) {
                e.printStackTrace();
                this.logger.warning("Could not sleep on featured games: " + e.getMessage());
            }
        }).start();
    }


    @PostConstruct
    @RequiresInitialSetup
    public void cleanOrphanSummoners() {
        this.logger.info("Limpiando ligas huérfanas...");
        List<League> orphanLeagues = this.leagueRepository.findAllByRetrievingIsTrue();
        for (League orphanLeague : orphanLeagues) {
            orphanLeague.setRetrieving(false);
            this.leagueRepository.save(orphanLeague);
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(fixedRate = 500, initialDelay = 500)
    public void getSummonerInfo() {
        League league = this.leagueRepository.findTopByRetrievingIsFalseAndDisabledIsFalseOrderByLastTimeUpdated();
        if (league == null) {
            if (!noLeaguesMessageShown) {
                this.logger.info("No leagues to update.");
                noLeaguesMessageShown = true;
            }
            return;
        }
        if (!league.getLastTimeUpdated().plusMinutes(3).isBefore(LocalDateTime.now())) {
            return;
        }
        league.setRetrieving(true);
        league = this.leagueRepository.save(league);
        this.logger.info("Updating league " + league.getRiotId());
        noLeaguesMessageShown = false;
        League summonerToken = this.leaguesConnector.updateLeague(league);
        if (summonerToken == null) {
            return;
        }

        league.setRetrieving(false);
        this.leagueRepository.save(league);
    }

}
