package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.GameItem;
import com.onlol.fetcher.model.GameItemLanguage;
import com.onlol.fetcher.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemLanguageRepository extends JpaRepository<GameItemLanguage, Long> {
    GameItemLanguage findByGameItemAndLanguage(GameItem gameItem, Language language);
}