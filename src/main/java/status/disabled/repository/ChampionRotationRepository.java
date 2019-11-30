package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.Champion;
import status.disabled.model.ChampionRotation;
import status.disabled.model.Region;

@Repository
public interface ChampionRotationRepository extends JpaRepository<ChampionRotation, Long> {
    ChampionRotation findByRotationDateAndRegionAndChampionAndForNewPlayers(String rotationDate, Region region, Champion champion, boolean forNewPlayers);
}