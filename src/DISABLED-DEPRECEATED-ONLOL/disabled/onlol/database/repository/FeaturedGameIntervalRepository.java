package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.FeaturedGameInterval;
import status.disabled.onlol.database.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturedGameIntervalRepository extends JpaRepository<FeaturedGameInterval, Integer> {
    FeaturedGameInterval findByRegion(Region region);
}