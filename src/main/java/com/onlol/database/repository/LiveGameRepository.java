package com.onlol.database.repository;

import com.onlol.database.model.LiveGame;
import com.onlol.database.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveGameRepository extends JpaRepository<LiveGame, Integer> {
    LiveGame findTopByGameIdAndRegion(Long gameId, Region region);
}