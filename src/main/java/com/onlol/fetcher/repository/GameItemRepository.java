package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.GameItem;
import com.onlol.fetcher.model.GameVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemRepository extends JpaRepository<GameItem, Long> {
    GameItem findByGameVersionAndItemId(GameVersion gameVersion, Integer itemId);
}