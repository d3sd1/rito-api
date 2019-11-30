package status.disabled.onlol.fetcher.scrapers;

import status.disabled.onlol.database.model.MatchGame;
import status.disabled.onlol.database.repository.MatchGameRepository;
import status.disabled.onlol.fetcher.api.connector.MatchConnector;
import status.disabled.onlol.fetcher.firstrun.RequiresInitialSetup;
import status.disabled.onlol.fetcher.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

    private boolean noMatchesMessageShown;

    @PostConstruct
    @RequiresInitialSetup
    public void clearOrphanGames() {
        this.logger.info("Limpiando partidas huérfanas...");
        List<MatchGame> orphanGames = this.matchGameRepository.findAllByRetrievedIsFalseAndRetrievingIsTrue();
        for(MatchGame matchGame:orphanGames) {
            matchGame.setRetrieving(false);
            this.matchGameRepository.save(matchGame);
        }
    }

    @Async
    @Lazy
    @RequiresInitialSetup
    @Scheduled(fixedRate = 1000, initialDelay = 500)
    public void getMatches() {
        MatchGame matchGame = this.matchGameRepository.findTopByRetrievedIsFalseAndRetrievingIsFalse();
        if(matchGame == null) {
            if (!this.noMatchesMessageShown) {
                this.logger.info("No matches to update...");
                this.noMatchesMessageShown = true;
            }
            return;
        }
        matchGame.setRetrieving(true);
        this.matchGameRepository.save(matchGame);
        this.logger.info("Updating match: " + matchGame.getGameId());
        this.noMatchesMessageShown = false;

        matchGame = this.matchConnector.match(matchGame);
        MatchGame sampleMatchGame = this.matchConnector.match(matchGame);
        this.matchConnector.matchGameTimeline(matchGame);
    }

}
