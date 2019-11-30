package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.Champion;
import status.disabled.unknown.model.ChampionStats;
import status.disabled.unknown.model.GameVersion;

@Repository
public interface ChampionStatsRepository extends JpaRepository<ChampionStats, Integer> {
    ChampionStats findByChampionAndGameVersion(Champion champion, GameVersion gameVersion);
}