package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.GameItem;
import com.onlol.fetcher.api.model.Summoner;
import com.onlol.fetcher.api.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemRepository extends JpaRepository<GameItem, Long> {
    GameItem findByVersionAndItemId(Version version, Integer itemId);
}