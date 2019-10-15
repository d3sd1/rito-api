package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.MatchGame;
import com.onlol.fetcher.api.model.MatchGameTeam;
import com.onlol.fetcher.api.model.MatchGameTeamStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchGameTeamStatsRepository extends JpaRepository<MatchGameTeamStats, Long> {
    MatchGameTeamStats findByGameIdAndTeam(Long gameId, MatchGameTeam team);
}