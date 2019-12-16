package com.global.api;

import com.global.model.ApiKey;
import com.global.repository.ApiKeyRepository;
import com.global.services.Logger;
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
    private Logger logger;

    public ApiKey getKey() {
        ApiKey apiKey = this.apiKeyRepository.findTopByBannedIsFalseAndValidIsTrueOrderByLastTimestampUsedAsc();

        if (apiKey == null) {
            apiKey = this.apiKeyRepository.findTopByBannedIsTrueOrderByRetryAfter();
        }
        if (apiKey == null) {
            this.logger.error("All api keys are invalid.");
        }
        if (this.apiKeyRepository.findAll().isEmpty()) {
            this.logger.error("No api keys found...");
        } else {
            apiKey.setLastTimestampUsed(System.currentTimeMillis());
            this.apiKeyRepository.save(apiKey);
        }
        return apiKey;
    }
}
