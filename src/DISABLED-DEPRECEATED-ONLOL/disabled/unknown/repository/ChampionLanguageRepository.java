package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.Champion;
import status.disabled.unknown.model.ChampionLanguage;
import status.disabled.unknown.model.GameVersion;
import status.disabled.unknown.model.Language;

@Repository
public interface ChampionLanguageRepository extends JpaRepository<ChampionLanguage, Integer> {
    ChampionLanguage findByChampionAndLanguageAndGameVersion(Champion champion, Language language, GameVersion gameVersion);
}