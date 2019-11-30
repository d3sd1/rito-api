package status.disabled.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import status.disabled.model.GameItem;
import status.disabled.model.GameItemLanguage;
import status.disabled.model.Language;

@Repository
public interface GameItemLanguageRepository extends JpaRepository<GameItemLanguage, Long> {
    GameItemLanguage findByGameItemAndLanguage(GameItem gameItem, Language language);
}