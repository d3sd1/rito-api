package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.Platform;
import com.onlol.fetcher.api.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository extends JpaRepository<Queue, String> {
    Queue findTopByQueueId(Integer queueId);
}