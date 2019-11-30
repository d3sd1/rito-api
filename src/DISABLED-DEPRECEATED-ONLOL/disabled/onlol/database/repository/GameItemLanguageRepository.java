package status.disabled.onlol.database.repository;

import status.disabled.onlol.database.model.GameItem;
import status.disabled.onlol.database.model.GameItemLanguage;
import status.disabled.onlol.database.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemLanguageRepository extends JpaRepository<GameItemLanguage, Long> {
    GameItemLanguage findByGameItemAndLanguage(GameItem gameItem, Language language);
}