package com.onlol.database.repository;

import com.onlol.database.model.GameQueueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameQueueTypeRepository extends JpaRepository<GameQueueType, Integer> {
    GameQueueType findByKeyName(String keyName);
}