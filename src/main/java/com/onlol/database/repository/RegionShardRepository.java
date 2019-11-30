package com.onlol.database.repository;

import com.onlol.database.model.GameVersion;
import com.onlol.database.model.Region;
import com.onlol.database.model.RegionShard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardRepository extends JpaRepository<RegionShard, Integer> {
    RegionShard findByRegionAndGameVersion(Region region, GameVersion gameVersion);
}