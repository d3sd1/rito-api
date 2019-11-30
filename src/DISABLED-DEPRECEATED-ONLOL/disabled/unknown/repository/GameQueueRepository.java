package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.GameQueue;

@Repository
public interface GameQueueRepository extends JpaRepository<GameQueue, Integer> {
    GameQueue findTopByQueueId(Integer queueId);
}