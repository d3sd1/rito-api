package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.Language;
import status.disabled.model.SummonerSpell;
import status.disabled.model.SummonerSpellLanguage;

@Repository
public interface SummonerSpellLanguageRepository extends JpaRepository<SummonerSpellLanguage, Integer> {
    SummonerSpellLanguage findBySummonerSpellAndLanguage(SummonerSpell summonerSpell, Language language);
}