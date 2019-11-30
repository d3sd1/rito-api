package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.Champion;
import status.disabled.model.ChampionStats;
import status.disabled.model.GameVersion;

@Repository
public interface ChampionStatsRepository extends JpaRepository<ChampionStats, Integer> {
    ChampionStats findByChampionAndGameVersion(Champion champion, GameVersion gameVersion);
}