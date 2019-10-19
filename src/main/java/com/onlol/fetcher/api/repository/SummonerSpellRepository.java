package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.SummonerSpell;
import com.onlol.fetcher.api.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerSpellRepository extends JpaRepository<SummonerSpell, Integer> {
    SummonerSpell findByIdAndVersion(Integer id, Version version);
}