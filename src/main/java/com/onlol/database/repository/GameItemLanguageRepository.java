package com.onlol.database.repository;

import com.onlol.database.model.GameItem;
import com.onlol.database.model.GameItemLanguage;
import com.onlol.database.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemLanguageRepository extends JpaRepository<GameItemLanguage, Long> {
    GameItemLanguage findByGameItemAndLanguage(GameItem gameItem, Language language);
}