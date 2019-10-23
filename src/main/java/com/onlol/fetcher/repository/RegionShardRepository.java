package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Region;
import com.onlol.fetcher.model.RegionShard;
import com.onlol.fetcher.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardRepository extends JpaRepository<RegionShard, Integer> {
    RegionShard findByRegionAndVersion(Region region, Version version);
}