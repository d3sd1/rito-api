package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Integer> {
    League findByRiotId(String riotId);
}