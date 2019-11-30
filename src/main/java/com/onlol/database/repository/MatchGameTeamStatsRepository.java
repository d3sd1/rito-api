package com.onlol.database.repository;

import com.onlol.database.model.MatchGameTeamStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchGameTeamStatsRepository extends JpaRepository<MatchGameTeamStats, Long> {

}