package com.onlol.database.repository;

import com.onlol.database.model.MatchGameTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchGameTeamRepository extends JpaRepository<MatchGameTeam, Integer> {
    MatchGameTeam findByTeamId(Integer teamId);
}