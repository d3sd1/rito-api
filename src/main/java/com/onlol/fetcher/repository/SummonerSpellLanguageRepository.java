package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Language;
import com.onlol.fetcher.model.SummonerSpell;
import com.onlol.fetcher.model.SummonerSpellLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerSpellLanguageRepository extends JpaRepository<SummonerSpellLanguage, Integer> {
    SummonerSpellLanguage findBySummonerSpellAndLanguage(SummonerSpell summonerSpell, Language language);
}