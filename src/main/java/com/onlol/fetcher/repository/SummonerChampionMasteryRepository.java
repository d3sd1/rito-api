package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Champion;
import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.model.SummonerChampionMastery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummonerChampionMasteryRepository extends JpaRepository<SummonerChampionMastery, Long> {
    SummonerChampionMastery findBySummonerAndChampion(Summoner summoner, Champion champion);

    List<SummonerChampionMastery> findBySummoner(Summoner summoner);
}