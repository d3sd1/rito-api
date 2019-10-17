package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.ApiKey;
import com.onlol.fetcher.api.model.Queue;
import com.onlol.fetcher.api.model.Summoner;
import com.onlol.fetcher.api.model.SummonerLeague;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerLeagueRepository extends JpaRepository<SummonerLeague, Long> {
    SummonerLeague findBySummonerAndQueue(Summoner summoner, Queue queue);
}