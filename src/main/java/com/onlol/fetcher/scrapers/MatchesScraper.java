package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.MatchConnector;
import com.onlol.fetcher.api.model.MatchGame;
import com.onlol.fetcher.api.repository.MatchGameRepository;
import com.onlol.fetcher.api.sampleModel.SampleMatchGame;
import com.onlol.fetcher.firstrun.RequiresInitialSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
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

    @PostConstruct
    @RequiresInitialSetup
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
        System.out.println("Retrieving matches...");
        MatchGame matchGame = this.matchGameRepository.findTopByRetrievedIsFalseAndRetrievingIsFalse();
        if(matchGame == null) {
            System.out.println("No matches to update...");
            return;
        }
        matchGame.setRetrieving(true);
        this.matchGameRepository.save(matchGame);
        System.out.println("Updating match: " + matchGame.getGameId());

        MatchGame sampleMatchGame = this.matchConnector.match(matchGame.getGameId());
        //TODO: recuperar timeline GET /lol/match/v4/timelines/by-match/{matchId}
    }
}
