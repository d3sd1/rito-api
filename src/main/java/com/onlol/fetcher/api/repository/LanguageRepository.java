package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.Language;
import com.onlol.fetcher.api.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Language findByKeyName(String language);
}