package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.Champion;
import status.disabled.model.ChampionLanguage;
import status.disabled.model.GameVersion;
import status.disabled.model.Language;

@Repository
public interface ChampionLanguageRepository extends JpaRepository<ChampionLanguage, Integer> {
    ChampionLanguage findByChampionAndLanguageAndGameVersion(Champion champion, Language language, GameVersion gameVersion);
}