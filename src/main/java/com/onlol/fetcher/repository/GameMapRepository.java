package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.GameMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMapRepository extends JpaRepository<GameMap, Integer> {
    GameMap findTopByMapId(Integer mapId);

    GameMap findByMapName(String mapName);
}