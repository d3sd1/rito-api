package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Language findByKeyName(String language);
}