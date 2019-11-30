package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.RegionShardTranslation;

@Repository
public interface RegionShardTranslationRepository extends JpaRepository<RegionShardTranslation, Integer> {
}