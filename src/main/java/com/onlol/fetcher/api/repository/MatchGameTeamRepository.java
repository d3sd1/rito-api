package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.MatchGameTeam;
import com.onlol.fetcher.api.model.MatchGameTeamStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchGameTeamRepository extends JpaRepository<MatchGameTeam, Integer> {
    MatchGameTeam findByTeamId(Integer teamId);
}