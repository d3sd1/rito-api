package com.onlol.database.repository;

import com.onlol.database.model.GameVersion;
import com.onlol.database.model.SummonerSpell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerSpellRepository extends JpaRepository<SummonerSpell, Integer> {
    SummonerSpell findByIdAndGameVersion(Integer id, GameVersion gameVersion);
}