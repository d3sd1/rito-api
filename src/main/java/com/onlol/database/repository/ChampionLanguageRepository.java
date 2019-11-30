package com.onlol.database.repository;

import com.onlol.database.model.Champion;
import com.onlol.database.model.ChampionLanguage;
import com.onlol.database.model.GameVersion;
import com.onlol.database.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionLanguageRepository extends JpaRepository<ChampionLanguage, Integer> {
    ChampionLanguage findByChampionAndLanguageAndGameVersion(Champion champion, Language language, GameVersion gameVersion);
}