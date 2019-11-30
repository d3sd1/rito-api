package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.GameVersion;
import status.disabled.onlol.database.model.Region;
import status.disabled.onlol.database.model.RegionShard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardRepository extends JpaRepository<RegionShard, Integer> {
    RegionShard findByRegionAndGameVersion(Region region, GameVersion gameVersion);
}