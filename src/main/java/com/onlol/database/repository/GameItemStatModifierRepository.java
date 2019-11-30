package com.onlol.database.repository;

import com.onlol.database.model.GameItem;
import com.onlol.database.model.GameItemStat;
import com.onlol.database.model.GameItemStatModifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemStatModifierRepository extends JpaRepository<GameItemStatModifier, Long> {
    GameItemStatModifier findByGameItemAndGameItemStat(GameItem gameItem, GameItemStat gameItemStat);
}