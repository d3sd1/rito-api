package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.GameQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameQueueRepository extends JpaRepository<GameQueue, Integer> {
    GameQueue findTopByQueueId(Integer queueId);
}