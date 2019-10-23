package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.MatchGameTeamBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchGameTeamBanRepository extends JpaRepository<MatchGameTeamBan, Long> {

}