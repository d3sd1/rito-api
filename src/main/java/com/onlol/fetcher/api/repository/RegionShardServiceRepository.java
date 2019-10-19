package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.Region;
import com.onlol.fetcher.api.model.RegionShard;
import com.onlol.fetcher.api.model.RegionShardService;
import com.onlol.fetcher.api.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardServiceRepository extends JpaRepository<RegionShardService, Integer> {
}