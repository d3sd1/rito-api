package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.LiveGame;
import com.onlol.fetcher.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGameRepository extends JpaRepository<LiveGame, Integer> {
    LiveGame findTopByGameIdAndRegion(Long gameId, Region region);
}