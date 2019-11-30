package com.onlol.database.repository;

import com.onlol.database.model.Champion;
import com.onlol.database.model.Summoner;
import com.onlol.database.model.SummonerChampionMastery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummonerChampionMasteryRepository extends JpaRepository<SummonerChampionMastery, Long> {
    SummonerChampionMastery findBySummonerAndChampion(Summoner summoner, Champion champion);

    List<SummonerChampionMastery> findBySummoner(Summoner summoner);
}