package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.RegionShardService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardServiceRepository extends JpaRepository<RegionShardService, Integer> {
}