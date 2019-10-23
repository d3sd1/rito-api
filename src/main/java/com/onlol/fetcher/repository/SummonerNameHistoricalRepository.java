package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.model.SummonerNameHistorical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerNameHistoricalRepository extends JpaRepository<SummonerNameHistorical, String> {
    SummonerNameHistorical findTopByNameAndSummoner(String name, Summoner summoner);
}