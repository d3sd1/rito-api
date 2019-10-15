package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.GameMode;
import com.onlol.fetcher.api.model.GameType;
import com.onlol.fetcher.api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTypeRepository extends JpaRepository<GameType, String> {
    GameType findByGametype(String gametype);
}