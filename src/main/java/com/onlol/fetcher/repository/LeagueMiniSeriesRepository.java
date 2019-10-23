package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.LeagueMiniSeries;
import com.onlol.fetcher.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueMiniSeriesRepository extends JpaRepository<LeagueMiniSeries, Integer> {
    LeagueMiniSeries findBySummoner(Summoner summoner);
}