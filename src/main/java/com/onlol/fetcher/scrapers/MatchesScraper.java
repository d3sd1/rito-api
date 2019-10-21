package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.MatchConnector;
import com.onlol.fetcher.api.model.MatchGame;
import com.onlol.fetcher.api.repository.MatchGameRepository;
import com.onlol.fetcher.firstrun.RequiresInitialSetup;
import com.onlol.fetcher.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@EnableAsync
public class MatchesScraper {
    @Autowired
    private MatchGameRepository matchGameRepository;


    @Autowired
    private MatchConnector matchConnector;

    @Autowired
    private LogService logger;

    @PostConstruct
    @RequiresInitialSetup
    @Scheduled(cron = "0 1 1 * * ?")
    @Async
    public void clearOrphanGames() {
        System.out.println("Limpiando partidas huérfanas...");
        List<MatchGame> orphanGames = this.matchGameRepository.findAllByRetrievedIsFalseAndRetrievingIsTrue();
        for(MatchGame matchGame:orphanGames) {
            matchGame.setRetrieving(false);
            this.matchGameRepository.save(matchGame);
        }
    }

    @Async
    @RequiresInitialSetup
    @Scheduled(fixedRate = 5000, initialDelay = 10000)
    public void getMatches() {
        this.logger.info("Retrieving matches...");
        MatchGame matchGame = this.matchGameRepository.findTopByRetrievedIsFalseAndRetrievingIsFalse();
        if(matchGame == null) {
            this.logger.info("No matches to update...");
            return;
        }
        matchGame.setRetrieving(true);
        this.matchGameRepository.save(matchGame);
        this.logger.info("Updating match: " + matchGame.getGameId());

        matchGame = this.matchConnector.match(matchGame);
        MatchGame sampleMatchGame = this.matchConnector.match(matchGame);

        //TODO: recuperar timeline GET /lol/match/v4/timelines/by-match/{matchId}
    }
}
