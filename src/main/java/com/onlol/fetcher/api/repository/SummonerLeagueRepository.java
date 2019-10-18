package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerLeagueRepository extends JpaRepository<SummonerLeague, Long> {
    SummonerLeague findBySummonerAndQueue(Summoner summoner, Queue queue);
    SummonerLeague findBySummonerAndQueueType(Summoner summoner, QueueType queueType);
}