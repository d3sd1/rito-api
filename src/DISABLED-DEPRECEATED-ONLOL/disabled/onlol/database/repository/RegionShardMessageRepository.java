package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.RegionShardMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardMessageRepository extends JpaRepository<RegionShardMessage, Integer> {
}