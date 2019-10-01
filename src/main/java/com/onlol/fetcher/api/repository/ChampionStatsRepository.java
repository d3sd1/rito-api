package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.Champion;
import com.onlol.fetcher.api.model.ChampionStats;
import com.onlol.fetcher.api.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionStatsRepository extends JpaRepository<ChampionStats, Integer> {
    ChampionStats findByChampionAndVersion(Champion champion, Version version);
}