package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.RegionShardTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionShardTranslationRepository extends JpaRepository<RegionShardTranslation, Integer> {
}