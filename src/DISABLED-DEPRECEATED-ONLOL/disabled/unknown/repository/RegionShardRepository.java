package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.GameVersion;
import status.disabled.unknown.model.Region;
import status.disabled.unknown.model.RegionShard;

@Repository
public interface RegionShardRepository extends JpaRepository<RegionShard, Integer> {
    RegionShard findByRegionAndGameVersion(Region region, GameVersion gameVersion);
}