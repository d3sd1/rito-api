package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    Region findByServicePlatform(String servicePlatform);
    Region findByServiceRegion(String serviceRegion);
}