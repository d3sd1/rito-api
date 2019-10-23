package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.GameQueueType;
import com.onlol.fetcher.model.Summoner;
import com.onlol.fetcher.model.SummonerLeague;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerLeagueRepository extends JpaRepository<SummonerLeague, Long> {
    SummonerLeague findBySummonerAndQueueType(Summoner summoner, GameQueueType gameQueueType);
}