package status.disabled.unknown.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.unknown.model.GameItem;
import status.disabled.unknown.model.GameItemLanguage;
import status.disabled.unknown.model.Language;

@Repository
public interface GameItemLanguageRepository extends JpaRepository<GameItemLanguage, Long> {
    GameItemLanguage findByGameItemAndLanguage(GameItem gameItem, Language language);
}