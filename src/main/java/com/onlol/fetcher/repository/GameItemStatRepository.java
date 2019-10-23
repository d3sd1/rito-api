package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.GameItemStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemStatRepository extends JpaRepository<GameItemStat, Long> {
    GameItemStat findByKeyName(String keyName);
}