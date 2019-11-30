package status.disabled.unknown.fetcher.scrapers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import status.disabled.unknown.fetcher.api.connector.MatchConnector;
import status.disabled.unknown.fetcher.firstrun.RequiresInitialSetup;
import status.disabled.unknown.fetcher.logger.LogService;
import status.disabled.unknown.model.MatchGame;
import status.disabled.unknown.repository.MatchGameRepository;

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
        for (MatchGame matchGame : orphanGames) {
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
        if (matchGame == null) {
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
