package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.MatchList;
import com.onlol.fetcher.api.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchListRepository extends JpaRepository<MatchList, String> {
    MatchList findByMatchGameIdAndSummonerAccountId(Long matchId, String summonerId);
}