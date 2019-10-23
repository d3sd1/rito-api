package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.model.SummonerChampionMastery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerChampionMasteryRepository extends JpaRepository<SummonerChampionMastery, Long> {
    SummonerChampionMastery findBySummoner(Summoner summoner);
}