package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.model.SummonerChampionMasteryLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerChampionMasteryLevelRepository extends JpaRepository<SummonerChampionMasteryLevel, Long> {
    SummonerChampionMasteryLevel findBySummoner(Summoner summoner);
}