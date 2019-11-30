package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.LiveGame;
import status.disabled.model.Region;

@Repository
public interface LiveGameRepository extends JpaRepository<LiveGame, Integer> {
    LiveGame findTopByGameIdAndRegion(Long gameId, Region region);
}