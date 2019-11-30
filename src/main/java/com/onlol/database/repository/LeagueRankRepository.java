package com.onlol.database.repository;

import com.onlol.database.model.LeagueRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRankRepository extends JpaRepository<LeagueRank, Integer> {
    LeagueRank findByKeyName(String keyName);
}