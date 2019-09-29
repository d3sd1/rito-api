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
        System.out.println("Retrieving games...");
        MatchGame matchGame = this.matchGameRepository.findTopByRetrievedIsFalseAndRetrievingIsFalse();
        matchGame.setRetrieving(true);
        this.matchGameRepository.save(matchGame);

        MatchGame sampleMatchGame = this.matchConnector.match(matchGame.getGameId());
        //TODO: recuperar partidas
        //TODO: meter jugadores pendientes de datos a la tabla summoners con lastUpdate = 0
        //TODO: cron para que con los lastUpdate se recupern si el sumoner no se actualizo! (actualizar summoners + antiguos)
        /*
        *
        *
        matchGame.setRetrieved(true);
        matchGame.setRetrieving(false);
        this.matchGameRepository.save(matchGame);
        * */

    }
}
