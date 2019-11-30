package com.onlol.database.repository;

import com.onlol.database.model.Champion;
import com.onlol.database.model.ChampionStats;
import com.onlol.database.model.GameVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionStatsRepository extends JpaRepository<ChampionStats, Integer> {
    ChampionStats findByChampionAndGameVersion(Champion champion, GameVersion gameVersion);
}