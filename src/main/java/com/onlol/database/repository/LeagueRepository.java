package com.onlol.database.repository;

import com.onlol.database.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeagueRepository extends JpaRepository<League, Integer> {
    League findByRiotId(String riotId);

    List<League> findAllByRetrievingIsTrue();

    League findTopByRetrievingIsFalseAndDisabledIsFalseOrderByLastTimeUpdated();
}