package com.onlol.fetcher.api.repository;

import com.onlol.fetcher.api.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, String> {
    ApiKey findTopByBannedIsFalse();

    ApiKey findTopByretryAfterOrderByRetryAfterAsc();
}