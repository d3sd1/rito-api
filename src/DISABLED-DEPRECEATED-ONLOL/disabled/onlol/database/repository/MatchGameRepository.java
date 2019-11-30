package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.MatchGame;
import status.disabled.onlol.database.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchGameRepository extends JpaRepository<MatchGame, Long> {
    MatchGame findByGameId(Long gameId);

    MatchGame findByGameIdAndRegion(Long gameId, Region region);

    MatchGame findTopByRetrievedIsFalseAndRetrievingIsFalse();

    List<MatchGame> findAllByRetrievedIsFalseAndRetrievingIsTrue();
}