package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.GameQueueType;

@Repository
public interface GameQueueTypeRepository extends JpaRepository<GameQueueType, Integer> {
    GameQueueType findByKeyName(String keyName);
}