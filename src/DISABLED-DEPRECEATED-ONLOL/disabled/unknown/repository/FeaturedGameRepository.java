package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.FeaturedGame;
import status.disabled.unknown.model.Region;

@Repository
public interface FeaturedGameRepository extends JpaRepository<FeaturedGame, Integer> {
    FeaturedGame findByGameIdAndRegion(Long gameId, Region region);
}