package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.GameVersion;
import com.onlol.fetcher.model.SummonerSpell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerSpellRepository extends JpaRepository<SummonerSpell, Integer> {
    SummonerSpell findByIdAndGameVersion(Integer id, GameVersion gameVersion);
}