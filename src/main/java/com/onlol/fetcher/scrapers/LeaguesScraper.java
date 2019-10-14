package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.MatchConnector;
import com.onlol.fetcher.api.connector.SummonerConnector;
import com.onlol.fetcher.api.model.Summoner;
import com.onlol.fetcher.api.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@EnableAsync
public class LeaguesScraper {

    @Autowired
    private SummonerConnector summonerConnector;

    @Autowired
    private MatchConnector matchConnector;

    @Autowired
    private SummonerRepository summonerRepository;

    @Async
    @Scheduled(fixedRate = 5000)
    public void getSummonerInfo() {

        //TODO: recuperar los siguientes endpoints... y añadir los usuarios a la tabla para actualizar
        /*
         /lol/league/v4/masterleagues/by-queue/{queue}
          /lol/league/v4/challengerleagues/by-queue/{queue}
           /lol/league/v4/grandmasterleagues/by-queue/{queue}
         */
    }
    //TODO: reecuperar featured games
    // GET /lol/spectator/v4/featured-games


}
