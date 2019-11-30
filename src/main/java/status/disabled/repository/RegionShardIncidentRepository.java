package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.RegionShardIncident;

@Repository
public interface RegionShardIncidentRepository extends JpaRepository<RegionShardIncident, Integer> {
}