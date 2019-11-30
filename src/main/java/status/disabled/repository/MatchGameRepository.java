package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.MatchGame;
import status.disabled.model.Region;

import java.util.List;

@Repository
public interface MatchGameRepository extends JpaRepository<MatchGame, Long> {
    MatchGame findByGameId(Long gameId);

    MatchGame findByGameIdAndRegion(Long gameId, Region region);
    MatchGame findTopByRetrievedIsFalseAndRetrievingIsFalse();
    List<MatchGame> findAllByRetrievedIsFalseAndRetrievingIsTrue();
}