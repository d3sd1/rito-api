package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.RegionShardIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardIncidentRepository extends JpaRepository<RegionShardIncident, Integer> {
}