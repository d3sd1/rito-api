package com.onlol.database.repository;

import com.onlol.database.model.ChampionBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionBanRepository extends JpaRepository<ChampionBan, Integer> {
}