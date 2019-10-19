package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.Language;
import com.onlol.fetcher.api.model.SummonerSpell;
import com.onlol.fetcher.api.model.SummonerSpellLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerSpellLanguageRepository extends JpaRepository<SummonerSpellLanguage, Integer> {
    SummonerSpellLanguage findBySummonerSpellAndLanguage(SummonerSpell summonerSpell, Language language);
}