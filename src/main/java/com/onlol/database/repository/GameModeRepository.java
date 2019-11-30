package com.onlol.database.repository;

import com.onlol.database.model.GameMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameModeRepository extends JpaRepository<GameMode, String> {
    GameMode findByGameMode(String gameMode);
}