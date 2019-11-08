package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.ChampionBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionBanRepository extends JpaRepository<ChampionBan, Integer> {
}