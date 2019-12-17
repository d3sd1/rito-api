package com.global.api;

import com.global.model.ApiKey;
import com.global.model.ApiKeyAvailability;
import com.global.model.Platform;
import com.global.model.RiotGame;
import com.global.repository.ApiKeyAvailabilityRepository;
import com.global.repository.ApiKeyRateLimitsRepository;
import com.global.repository.ApiKeyRepository;
import com.global.services.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Esto se podría optimizar haciendo los rate limit por cada método en lugar de la app en general.
 */
@Service
public class ApiKeyManager {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private ApiKeyAvailabilityRepository apiKeyAvailabilityRepository;

    @Autowired
    private ApiKeyRateLimitsRepository apiKeyRateLimitsRepository;

    @Autowired
    private Logger logger;

    private List<ApiKey> apiKeys = new ArrayList<>();

    // Cache api keys so we lower the database calls so badly
    @Scheduled(fixedDelay = 60000)
    public void regenerateApiKeys() {
        this.apiKeys = this.apiKeyRepository.findAllByDisabledIsFalseAndConfiguredIsTrue();

        for (ApiKey apiKey : this.apiKeys) {
            // Regenerate api keys, so they won't get stuck with the same rate liimit and ignored. (every 30m)
            if (apiKey.getLastTimeUsed() != null && apiKey.getLastTimeUsed().plusMinutes(30).isBefore(LocalDateTime.now())) {
                apiKey.getApiKeyRateLimits().setNextRetry(null);
                apiKey.getApiKeyRateLimits().setAppRateLimitCount("0:0");
                apiKey.getApiKeyRateLimits().setAppRateLimitMax("0:0");
                apiKey.getApiKeyRateLimits().setMethodRateLimitCount("0:0");
                apiKey.getApiKeyRateLimits().setMethodRateLimitMax("0:0");
                this.apiKeyRateLimitsRepository.save(apiKey.getApiKeyRateLimits());
            }
        }
    }

    public ApiKey getKey(Platform platform, RiotGame riotGame) {
        // OK Tiene que ser para la plataforma (region) dada
        // OK no puede estar deshabilurada
        // OK tiene que ser para el juego dado
        // OK tiene que ser la que menos calls tenga
        // OK no puede estar en rate limit
        ApiKey validApiKey = null;

        // Reload api keys if empty, due to postinstall... per example.
        if (this.apiKeys.isEmpty()) {
            this.regenerateApiKeys();
        }

        // It doesn't has method level  Rate Limiting. Although we can track it, and implement if needed.
        for (ApiKey apiKey : this.apiKeys) {
            // Not suitable for this platform nor Not suitable for this game
            boolean available = false;
            for (ApiKeyAvailability apiKeyAvailability : apiKey.getAvailability()) {
                if (apiKeyAvailability.getPlatform().equals(platform) && apiKeyAvailability.getRiotGame().equals(riotGame)) {
                    available = true;
                }
            }
            if (!available) {
                continue;
            }

            // This api key is rate limited but it expired already.
            if (apiKey.getApiKeyRateLimits() != null && apiKey.getApiKeyRateLimits().getNextRetry() != null &&
                    apiKey.getApiKeyRateLimits().getNextRetry().isBefore(LocalDateTime.now())) {
                apiKey.getApiKeyRateLimits().setNextRetry(null);
                this.apiKeyRateLimitsRepository.save(apiKey.getApiKeyRateLimits());
                validApiKey = apiKey;
                continue;
            }

            // Never used api key (0:0), force (or used too early)
            if (apiKey.getLastTimeUsed() == null
                    || apiKey.getLastTimeUsed().plusMinutes(60).isBefore(LocalDateTime.now())) {
                validApiKey = apiKey;
                break; // <-- must be break due to we force to use this key.
            }

            // Base case
            if (validApiKey == null) {
                validApiKey = apiKey;
                continue;
            }

            // Setup rate limit conf
            int[] apiKeyLimits = Arrays.stream(apiKey.getApiKeyRateLimits().getAppRateLimitMax().split(",")[0].split(":")).mapToInt(Integer::parseInt).toArray();
            int[] apiKeyCurrent = Arrays.stream(apiKey.getApiKeyRateLimits().getAppRateLimitCount().split(",")[0].split(":")).mapToInt(Integer::parseInt).toArray();
            int[] validKeyLimits = Arrays.stream(validApiKey.getApiKeyRateLimits().getAppRateLimitMax().split(",")[0].split(":")).mapToInt(Integer::parseInt).toArray();
            int[] validKeyCurrent = Arrays.stream(validApiKey.getApiKeyRateLimits().getAppRateLimitCount().split(",")[0].split(":")).mapToInt(Integer::parseInt).toArray();
            Integer remainingApiKeyCalls = apiKeyLimits[0] - apiKeyCurrent[0];
            Integer remainingValidApiKeyCalls = validKeyLimits[0] - validKeyCurrent[0];

            // Regenerated api key (0:0), force.
            if (apiKey.getApiKeyRateLimits().getAppRateLimitMax().equalsIgnoreCase("0:0")
                    || apiKey.getApiKeyRateLimits().getAppRateLimitCount().equalsIgnoreCase("0:0")) {
                validApiKey = apiKey;
                break; // <-- must be break due to we force to use this key.
            }

            // This key has more calls remaining than valid (general application ban)
            if (remainingApiKeyCalls > remainingValidApiKeyCalls) {
                validApiKey = apiKey;
                continue;
            }

            // This api key is ban but valid is not.
            if (validApiKey.getApiKeyRateLimits().getNextRetry() == null && validApiKey.getApiKeyRateLimits().getNextRetry() != null) {
                continue;
            }
            // Valid api key is ban but this one is not.
            if (validApiKey.getApiKeyRateLimits().getNextRetry() != null && validApiKey.getApiKeyRateLimits().getNextRetry() == null) {
                validApiKey = apiKey;
                continue;
            }
            // Both api keys are ban, but this needs less time to get up again.
            if (validApiKey.getApiKeyRateLimits().getNextRetry() != null && apiKey.getApiKeyRateLimits().getNextRetry().isBefore(validApiKey.getApiKeyRateLimits().getNextRetry())) {
                validApiKey = apiKey;
                continue;
            }
        }

        if (this.apiKeys.isEmpty()) {
            this.logger.error(String.format("No  api keys found on database.", platform, riotGame));
        } else if (validApiKey == null) {
            this.logger.error(String.format("No valid api keys found for platform [%s] and game [%s]", platform, riotGame));
        } else if (validApiKey.getApiKeyRateLimits().getNextRetry() != null) {
            this.logger.error(String.format("All api keys found for platform [%s] and game [%s]. Next attempt available in %s", platform.getServiceRegion(), riotGame.getGameName(), validApiKey.getApiKeyRateLimits().getNextRetry()));
            validApiKey = null; // For preventing multiple 429 everytime.
        } else if (validApiKey.getApiKey().equalsIgnoreCase("")) {
            this.logger.error(String.format("No valid api keys found for platform [%s] and game [%s]", platform, riotGame));
        }
        if (validApiKey != null) {
            validApiKey.setLastTimeUsed(LocalDateTime.now());
            this.apiKeyRepository.save(validApiKey);
        }
        return validApiKey;
    }

    public void banKey(ApiKey apiKey, Platform platform, RiotGame riotGame) {
        // Ban key for current game and platform
        for (ApiKeyAvailability apiKeyAvailability : apiKey.getAvailability()) {
            if (apiKeyAvailability.getPlatform().equals(platform) && apiKeyAvailability.getRiotGame().equals(riotGame)) {
                apiKeyAvailability:
                apiKey.getAvailability().remove(apiKeyAvailability);
            }
        }

        if (apiKey.getAvailability().isEmpty()) {
            apiKey.setDisabled(true);
        }
        this.apiKeyRepository.save(apiKey);
    }
}
