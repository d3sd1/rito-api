package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.GameItem;
import com.onlol.fetcher.api.model.GameItemMap;
import com.onlol.fetcher.api.model.GameItemStat;
import com.onlol.fetcher.api.model.GameMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemStatRepository extends JpaRepository<GameItemStat, Long> {
    GameItemStat findByKeyName(String keyName);
}