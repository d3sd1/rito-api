package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.Language;
import status.disabled.onlol.database.model.SummonerSpell;
import status.disabled.onlol.database.model.SummonerSpellLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerSpellLanguageRepository extends JpaRepository<SummonerSpellLanguage, Integer> {
    SummonerSpellLanguage findBySummonerSpellAndLanguage(SummonerSpell summonerSpell, Language language);
}