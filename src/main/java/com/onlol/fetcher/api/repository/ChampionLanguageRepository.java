package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionLanguageRepository extends JpaRepository<ChampionLanguage, Integer> {
    ChampionLanguage findByChampionAndLanguageAndVersion(Champion champion, Language language, Version version);
}