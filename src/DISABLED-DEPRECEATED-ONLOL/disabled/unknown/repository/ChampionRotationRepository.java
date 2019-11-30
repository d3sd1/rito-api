package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.Champion;
import status.disabled.unknown.model.ChampionRotation;
import status.disabled.unknown.model.Region;

@Repository
public interface ChampionRotationRepository extends JpaRepository<ChampionRotation, Long> {
    ChampionRotation findByRotationDateAndRegionAndChampionAndForNewPlayers(String rotationDate, Region region, Champion champion, boolean forNewPlayers);
}