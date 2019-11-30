package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.Champion;
import status.disabled.onlol.database.model.ChampionLanguage;
import status.disabled.onlol.database.model.GameVersion;
import status.disabled.onlol.database.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionLanguageRepository extends JpaRepository<ChampionLanguage, Integer> {
    ChampionLanguage findByChampionAndLanguageAndGameVersion(Champion champion, Language language, GameVersion gameVersion);
}