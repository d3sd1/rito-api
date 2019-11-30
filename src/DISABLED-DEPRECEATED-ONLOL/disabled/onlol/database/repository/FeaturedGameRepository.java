package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.FeaturedGame;
import status.disabled.onlol.database.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturedGameRepository extends JpaRepository<FeaturedGame, Integer> {
    FeaturedGame findByGameIdAndRegion(Long gameId, Region region);
}