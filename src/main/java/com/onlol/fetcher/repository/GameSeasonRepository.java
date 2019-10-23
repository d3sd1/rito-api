package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.GameSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSeasonRepository extends JpaRepository<GameSeason, String> {
    GameSeason findTopById(Integer id);
}