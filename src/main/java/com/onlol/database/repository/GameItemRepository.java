package com.onlol.database.repository;

import com.onlol.database.model.GameItem;
import com.onlol.database.model.GameVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemRepository extends JpaRepository<GameItem, Long> {
    GameItem findByGameVersionAndItemId(GameVersion gameVersion, Integer itemId);
}