package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.MatchConnector;
import com.onlol.fetcher.api.model.MatchGame;
import com.onlol.fetcher.api.repository.MatchGameRepository;
import com.onlol.fetcher.api.sampleModel.SampleMatchGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class MatchesScraper {
    @Autowired
    private MatchGameRepository matchGameRepository;


    @Autowired
    private MatchConnector matchConnector;

    @PostConstruct
    @DependsOn("entityManagerFactory")
    private void clearOrphanGames() {
        List<MatchGame> orphanGames = this.matchGameRepository.findAllByRetrievedIsFalseAndRetrievingIsTrue();
        for(MatchGame matchGame:orphanGames) {
            matchGame.setRetrieving(false);
            this.matchGameRepository.save(matchGame);
        }
    }


    @Scheduled(fixedRate = 5000, initialDelay = 10000)
    public void ret() {
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

    }
}
