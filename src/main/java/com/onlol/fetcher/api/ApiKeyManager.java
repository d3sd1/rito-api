package com.onlol.fetcher.api;

import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.api.model.ApiKey;
import com.onlol.fetcher.api.repository.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyManager {
    @Autowired
    private ApiKeyRepository apiKeyRepository;
    @Autowired
    private LogService logger;

    public ApiKey getKey() {
        ApiKey apiKey = this.apiKeyRepository.findTopByBannedIsFalse();
        if(apiKey == null) {
            this.logger.error("No api keys found...");
        }
        return apiKey;
    }
}
