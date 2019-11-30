package com.onlol.database.repository;

import com.onlol.database.model.Summoner;
import com.onlol.database.model.SummonerChampionMasteryLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerChampionMasteryLevelRepository extends JpaRepository<SummonerChampionMasteryLevel, Long> {
    SummonerChampionMasteryLevel findBySummoner(Summoner summoner);
}