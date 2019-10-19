package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.LeagueMiniSeries;
import com.onlol.fetcher.api.model.QueueType;
import com.onlol.fetcher.api.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueMiniSeriesRepository extends JpaRepository<LeagueMiniSeries, Integer> {
    LeagueMiniSeries findBySummoner(Summoner summoner);
}