package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.League;
import com.onlol.fetcher.api.model.LeagueTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Integer> {
    League findByRiotId(String riotId);
}