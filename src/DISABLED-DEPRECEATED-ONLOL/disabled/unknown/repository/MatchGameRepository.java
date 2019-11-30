package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.MatchGame;
import status.disabled.unknown.model.Region;

import java.util.List;

@Repository
public interface MatchGameRepository extends JpaRepository<MatchGame, Long> {
    MatchGame findByGameId(Long gameId);

    MatchGame findByGameIdAndRegion(Long gameId, Region region);
    MatchGame findTopByRetrievedIsFalseAndRetrievingIsFalse();
    List<MatchGame> findAllByRetrievedIsFalseAndRetrievingIsTrue();
}