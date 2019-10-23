package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.ChampionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionTagRepository extends JpaRepository<ChampionTag, Integer> {
    ChampionTag findByKeyName(String keyName);
}