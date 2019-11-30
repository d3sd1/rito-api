package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.RegionShardService;

@Repository
public interface RegionShardServiceRepository extends JpaRepository<RegionShardService, Integer> {
}