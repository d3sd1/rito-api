package com.onlol.database.repository;

import com.onlol.database.model.LeagueMiniSeries;
import com.onlol.database.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueMiniSeriesRepository extends JpaRepository<LeagueMiniSeries, Integer> {
    LeagueMiniSeries findBySummoner(Summoner summoner);
}