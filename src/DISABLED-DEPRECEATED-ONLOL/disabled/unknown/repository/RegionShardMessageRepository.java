package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.RegionShardMessage;

@Repository
public interface RegionShardMessageRepository extends JpaRepository<RegionShardMessage, Integer> {
}