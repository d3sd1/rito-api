package com.onlol.database.repository;

import com.onlol.database.model.RegionShardService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardServiceRepository extends JpaRepository<RegionShardService, Integer> {
}