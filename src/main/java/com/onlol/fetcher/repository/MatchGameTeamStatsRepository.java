package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.MatchGameTeam;
import com.onlol.fetcher.model.MatchGameTeamStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchGameTeamStatsRepository extends JpaRepository<MatchGameTeamStats, Long> {
    MatchGameTeamStats findByGameIdAndTeam(Long gameId, MatchGameTeam team);
}