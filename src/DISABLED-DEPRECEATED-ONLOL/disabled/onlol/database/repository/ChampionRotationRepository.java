package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.Champion;
import status.disabled.onlol.database.model.ChampionRotation;
import status.disabled.onlol.database.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionRotationRepository extends JpaRepository<ChampionRotation, Long> {
    ChampionRotation findByRotationDateAndRegionAndChampionAndForNewPlayers(String rotationDate, Region region, Champion champion, boolean forNewPlayers);
}