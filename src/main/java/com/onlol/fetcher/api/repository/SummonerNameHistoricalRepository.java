package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.Summoner;
import com.onlol.fetcher.api.model.SummonerNameHistorical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerNameHistoricalRepository extends JpaRepository<SummonerNameHistorical, String> {
    SummonerNameHistorical findTopByNameAndSummoner(String name, Summoner summoner);
}