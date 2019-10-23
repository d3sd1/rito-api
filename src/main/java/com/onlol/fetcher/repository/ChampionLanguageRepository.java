package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Champion;
import com.onlol.fetcher.model.ChampionLanguage;
import com.onlol.fetcher.model.Language;
import com.onlol.fetcher.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionLanguageRepository extends JpaRepository<ChampionLanguage, Integer> {
    ChampionLanguage findByChampionAndLanguageAndVersion(Champion champion, Language language, Version version);
}