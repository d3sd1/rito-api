package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.FeaturedGameInterval;
import status.disabled.model.Region;

@Repository
public interface FeaturedGameIntervalRepository extends JpaRepository<FeaturedGameInterval, Integer> {
    FeaturedGameInterval findByRegion(Region region);
}