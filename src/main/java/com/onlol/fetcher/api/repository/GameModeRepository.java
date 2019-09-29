package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.GameMode;
import com.onlol.fetcher.api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameModeRepository extends JpaRepository<GameMode, String> {
    GameMode findByKeyName(String s);
}