package com.onlol.database.repository;

import com.onlol.database.model.RegionShardMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardMessageRepository extends JpaRepository<RegionShardMessage, Integer> {
}