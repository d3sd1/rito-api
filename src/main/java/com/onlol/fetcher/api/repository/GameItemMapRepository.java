package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.GameItem;
import com.onlol.fetcher.api.model.GameItemMap;
import com.onlol.fetcher.api.model.GameItemTag;
import com.onlol.fetcher.api.model.GameMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemMapRepository extends JpaRepository<GameItemMap, Long> {
    GameItemMap findByGameItemAndGameMap(GameItem gameItem, GameMap gameMap);
}