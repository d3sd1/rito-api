package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.ApiKey;
import com.onlol.fetcher.api.model.Champion;
import com.onlol.fetcher.api.model.ChampionLanguage;
import com.onlol.fetcher.api.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionLanguageRepository extends JpaRepository<ChampionLanguage, Integer> {
    ChampionLanguage findByChampionAndLanguage(Champion champion, Language language);
}