package com.onlol.fetcher.api;

import com.onlol.fetcher.api.model.ApiKey;
import com.onlol.fetcher.api.repository.ApiKeyRepository;
import com.onlol.fetcher.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
Esto se podría optimizar haciendo los rate limit por cada método en lugar de la app en general.
 */
@Service
public class ApiKeyManager {
    @Autowired
    private ApiKeyRepository apiKeyRepository;
    @Autowired
    private LogService logger;

    public ApiKey getKey() {
        ApiKey apiKey = this.apiKeyRepository.findTopByBannedIsFalse();
        if (apiKey == null) {
            apiKey = this.apiKeyRepository.findTopByBannedIsTrueOrderByRetryAfter();
        }
        if (this.apiKeyRepository.findAll().isEmpty()) {
            this.logger.error("No api keys found...");
        }
        return apiKey;
    }
}
