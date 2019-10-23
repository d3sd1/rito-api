package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.GameItem;
import com.onlol.fetcher.model.GameItemMap;
import com.onlol.fetcher.model.GameMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemMapRepository extends JpaRepository<GameItemMap, Long> {
    GameItemMap findByGameItemAndGameMap(GameItem gameItem, GameMap gameMap);
}