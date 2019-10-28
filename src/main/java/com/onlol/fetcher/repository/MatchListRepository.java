package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.MatchList;
import com.onlol.fetcher.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchListRepository extends JpaRepository<MatchList, Long> {
    MatchList findByMatchGameIdAndSummoner(Long matchId, Summoner summoner);
}