package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.MatchGameTeam;
import com.onlol.fetcher.api.model.MatchGameTeamBan;
import com.onlol.fetcher.api.model.MatchGameTeamStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchGameTeamBanRepository extends JpaRepository<MatchGameTeamBan, Long> {

}