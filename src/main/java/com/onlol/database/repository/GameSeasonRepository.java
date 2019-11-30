package com.onlol.database.repository;

import com.onlol.database.model.GameSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSeasonRepository extends JpaRepository<GameSeason, String> {
    GameSeason findTopById(Integer id);
}