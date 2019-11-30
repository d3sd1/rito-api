package com.onlol.database.repository;

import com.onlol.database.model.GameMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMapRepository extends JpaRepository<GameMap, Integer> {
    GameMap findTopByMapId(Integer mapId);

    GameMap findByMapName(String mapName);
}