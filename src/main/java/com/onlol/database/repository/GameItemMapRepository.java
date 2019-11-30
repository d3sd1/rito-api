package com.onlol.database.repository;

import com.onlol.database.model.GameItem;
import com.onlol.database.model.GameItemMap;
import com.onlol.database.model.GameMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemMapRepository extends JpaRepository<GameItemMap, Long> {
    GameItemMap findByGameItemAndGameMap(GameItem gameItem, GameMap gameMap);
}