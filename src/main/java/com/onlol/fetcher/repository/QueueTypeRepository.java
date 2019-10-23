package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.QueueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueTypeRepository extends JpaRepository<QueueType, Integer> {
    QueueType findByKeyName(String keyName);
}