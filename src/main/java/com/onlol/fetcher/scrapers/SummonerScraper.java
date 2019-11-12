package com.onlol.fetcher.scrapers;

import com.onlol.fetcher.api.connector.LeaguesConnector;
import com.onlol.fetcher.api.connector.SummonerConnector;
import com.onlol.fetcher.firstrun.RequiresInitialSetup;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.model.SummonerToken;
import com.onlol.fetcher.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableAsync
public class SummonerScraper {

    @Autowired
    private SummonerConnector summonerConnector;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private LeaguesConnector leaguesConnector;

    @Autowired
    private LogService logger;

    private boolean noSummonersMessageShown;

    @PostConstruct
    @RequiresInitialSetup
    public void cleanOrphanSummoners() {
        this.logger.info("Limpiando summoners huérfanos...");
        List<Summoner> orphanSummoners = this.summonerRepository.findAllByRetrievingIsTrue();
        for (Summoner orphanSummoner : orphanSummoners) {
            orphanSummoner.setRetrieving(false);
            this.summonerRepository.save(orphanSummoner);
        }
    }

    @Async
    @Lazy
    @RequiresInitialSetup
    @Scheduled(fixedRate = 5000, initialDelay = 500)
    public void getSummonerInfo() {
        Summoner summoner = this.summonerRepository.findTopByRetrievingIsFalseAndDisabledIsFalseOrderByLastTimeUpdated();
        if (summoner == null) {
            if (!noSummonersMessageShown) {
                this.logger.info("No summoners to update.");
                noSummonersMessageShown = true;
            }
            return;
        }
        if (!summoner.getLastTimeUpdated().plusMinutes(3).isBefore(LocalDateTime.now())) {
            return;
        }
        summoner.setRetrieving(true);
        summoner = this.summonerRepository.save(summoner);
        this.logger.info("Updating summoner " + summoner.getName());
        noSummonersMessageShown = false;
        SummonerToken summonerToken = this.summonerConnector.updateSummoner(summoner);
        if (summonerToken == null) {
            return;
        }
        this.leaguesConnector.summonerLeagues(summonerToken);
        this.summonerConnector.championMastery(summonerToken);
        this.summonerConnector.inGame(summonerToken);

        summoner = summonerToken.getSummoner();
        summoner.setRetrieving(false);
        this.summonerRepository.save(summoner);
    }


}
