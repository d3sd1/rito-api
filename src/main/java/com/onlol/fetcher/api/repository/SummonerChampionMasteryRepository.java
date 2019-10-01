package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.SummonerChampionMastery;
import com.onlol.fetcher.api.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerChampionMasteryRepository extends JpaRepository<SummonerChampionMastery, Long> {

}