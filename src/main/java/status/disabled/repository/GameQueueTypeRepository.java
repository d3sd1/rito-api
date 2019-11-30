package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameQueueType;

@Repository
public interface GameQueueTypeRepository extends JpaRepository<GameQueueType, Integer> {
    GameQueueType findByKeyName(String keyName);
}