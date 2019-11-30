package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.FeaturedGame;
import status.disabled.model.Region;

@Repository
public interface FeaturedGameRepository extends JpaRepository<FeaturedGame, Integer> {
    FeaturedGame findByGameIdAndRegion(Long gameId, Region region);
}