package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Champion;
import com.onlol.fetcher.model.ChampionStats;
import com.onlol.fetcher.model.GameVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionStatsRepository extends JpaRepository<ChampionStats, Integer> {
    ChampionStats findByChampionAndVersion(Champion champion, GameVersion gameVersion);
}