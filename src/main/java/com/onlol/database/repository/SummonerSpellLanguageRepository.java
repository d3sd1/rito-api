package com.onlol.database.repository;

import com.onlol.database.model.Language;
import com.onlol.database.model.SummonerSpell;
import com.onlol.database.model.SummonerSpellLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerSpellLanguageRepository extends JpaRepository<SummonerSpellLanguage, Integer> {
    SummonerSpellLanguage findBySummonerSpellAndLanguage(SummonerSpell summonerSpell, Language language);
}