package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.GameItem;
import com.onlol.fetcher.model.GameItemStat;
import com.onlol.fetcher.model.GameItemStatModifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemStatModifierRepository extends JpaRepository<GameItemStatModifier, Long> {
    GameItemStatModifier findByGameItemAndGameItemStat(GameItem gameItem, GameItemStat gameItemStat);
}