package com.onlol.fetcher.repository;

import com.onlol.fetcher.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, String> {
    ApiKey findTopByBannedIsFalse();

    ApiKey findTopByBannedIsTrueOrderByRetryAfter();
}