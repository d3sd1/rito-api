package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.RegionShardMessage;

@Repository
public interface RegionShardMessageRepository extends JpaRepository<RegionShardMessage, Integer> {
}