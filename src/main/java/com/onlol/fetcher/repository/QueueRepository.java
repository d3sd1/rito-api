package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Integer> {
    Queue findTopByQueueId(Integer queueId);
}