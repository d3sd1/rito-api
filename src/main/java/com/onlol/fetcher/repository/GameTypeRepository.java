package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTypeRepository extends JpaRepository<GameType, String> {
    GameType findByGametype(String gametype);
}