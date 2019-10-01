package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.ApiKey;
import com.onlol.fetcher.api.model.Champion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionRepository extends JpaRepository<Champion, Integer> {
    Champion findByChampId(Integer id);
}