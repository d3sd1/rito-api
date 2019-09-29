package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.GameMap;
import com.onlol.fetcher.api.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMapRepository extends JpaRepository<GameMap, Integer> {
    GameMap findTopById(Integer id);
}