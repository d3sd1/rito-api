package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.GameItem;
import com.onlol.fetcher.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemRepository extends JpaRepository<GameItem, Long> {
    GameItem findByVersionAndItemId(Version version, Integer itemId);
}