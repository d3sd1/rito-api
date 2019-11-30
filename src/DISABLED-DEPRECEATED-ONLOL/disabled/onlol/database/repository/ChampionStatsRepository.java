package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.Champion;
import status.disabled.onlol.database.model.ChampionStats;
import status.disabled.onlol.database.model.GameVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionStatsRepository extends JpaRepository<ChampionStats, Integer> {
    ChampionStats findByChampionAndGameVersion(Champion champion, GameVersion gameVersion);
}