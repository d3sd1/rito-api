package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameQueue;

@Repository
public interface GameQueueRepository extends JpaRepository<GameQueue, Integer> {
    GameQueue findTopByQueueId(Integer queueId);
}