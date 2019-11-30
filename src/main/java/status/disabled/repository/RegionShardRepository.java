package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameVersion;
import status.disabled.model.Region;
import status.disabled.model.RegionShard;

@Repository
public interface RegionShardRepository extends JpaRepository<RegionShard, Integer> {
    RegionShard findByRegionAndGameVersion(Region region, GameVersion gameVersion);
}