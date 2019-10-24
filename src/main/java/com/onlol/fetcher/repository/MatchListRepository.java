package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.MatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchListRepository extends JpaRepository<MatchList, String> {
    //TODO MatchList findByMatchGameIdAndSummonerAccountId(Long matchId, String summonerId);
}