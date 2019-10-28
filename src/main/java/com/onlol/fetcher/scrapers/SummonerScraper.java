package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.LeaguesConnector;
import com.onlol.fetcher.api.connector.MatchConnector;
import com.onlol.fetcher.api.connector.SummonerConnector;
import com.onlol.fetcher.firstrun.RequiresInitialSetup;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@EnableAsync
public class SummonerScraper {

    @Autowired
    private SummonerConnector summonerConnector;

    @Autowired
    private MatchConnector matchConnector;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private LeaguesConnector leaguesConnector;

    @Autowired
    private LogService logger;


    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    @RequiresInitialSetup
    @Scheduled(fixedRate = 5000)
    public void getSummonerInfo() {
        Summoner summoner = this.summonerRepository.findTopByOrderByLastTimeUpdated();
        if (summoner == null) {
            this.logger.info("No summoners to update.");
            return;
        }
        if (!summoner.getLastTimeUpdated().plusDays(7).isBefore(LocalDateTime.now())) {
            this.logger.info(summoner.getName() + " already up to date. No summoners to update, sleeping 30s...");

            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (Exception e) {

            }

            return;
        }
        this.logger.info("Updating summoner " + summoner.getName());
        this.summonerConnector.updateSummoner(summoner.getName(), summoner.getRegion());
        /*


        //TODO: en este fill rellenar usuario, ya que matchLists debe coger gameIDs, coger un gameId y
        // en esta funcion de baajo buscar un match. de ese match sacar real id y rellenar
        /* Recuperar todos los datos, y después actualizarlos en DB *
        // tambien contamos con apiSummonerDTO
        List<MatchList> matchLists = this.matchConnector.matchListByAccount(apiSummonerDTO, region);
        List<SummonerChampionMastery> summonerChampionMasteries = this.championMastery(summoner);
        List<SummonerLeague> summonerLeagues = this.leaguesConnector.summonerLeagues(summoner);
         */
    }


}
