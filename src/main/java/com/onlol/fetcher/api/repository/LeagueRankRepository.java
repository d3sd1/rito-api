package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.League;
import com.onlol.fetcher.api.model.LeagueRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRankRepository extends JpaRepository<LeagueRank, Integer> {
    LeagueRank findByKeyName(String keyName);
}