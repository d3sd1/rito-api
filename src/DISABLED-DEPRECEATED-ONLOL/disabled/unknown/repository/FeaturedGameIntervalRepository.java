package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.FeaturedGameInterval;
import status.disabled.unknown.model.Region;

@Repository
public interface FeaturedGameIntervalRepository extends JpaRepository<FeaturedGameInterval, Integer> {
    FeaturedGameInterval findByRegion(Region region);
}