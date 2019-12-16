package com.global.api;

import com.global.configuration.Constants;
import com.global.model.*;
import com.global.repository.ApiCallRepository;
import com.global.repository.ApiKeyRateLimitsRepository;
import com.global.repository.ApiKeyRepository;
import com.global.services.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class ApiConnector {

    @Autowired
    private ApiKeyManager apiKeyManager;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private Logger logger;


    @Autowired
    private ApiKeyRateLimitsRepository apiKeyRateLimitsRepository;

    @Autowired
    private ApiCallRepository apiCallRepository;

    private ResponseEntity<String> call(ApiEndpoint apiEndpoint, ApiKey apiKey, Platform platform, HttpMethod httpMethod, Object body) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = new ResponseEntity<String>(HttpStatus.SEE_OTHER);
        if (apiKey == null || apiEndpoint == null || platform == null) {
            return resp;
        }

        HttpEntity requestEntity = null;
        if (apiEndpoint.isRequiresApiKey()) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Riot-Token", apiKey.getApiKey());
            requestEntity = new HttpEntity<>(body, headers);
        } else {
            requestEntity = new HttpEntity<>(body);
        }
        try {
            //TODO: pass path parameters right to here
            resp = restTemplate.exchange(
                    apiEndpoint.getEndpoint().replace("{{hostUrl}}", platform.getHostName()).replace("{{summonerName}}", "nova desdi"),
                    httpMethod, requestEntity,
                    String.class);
            /* Fill Api Key params */
            ApiKeyRateLimits apiKeyRateLimits = apiKey.getApiKeyRateLimits();
            apiKeyRateLimits.setAppRateLimitCount(resp.getHeaders().get("X-App-Rate-Limit-Count").get(0));
            apiKeyRateLimits.setAppRateLimitMax(resp.getHeaders().get("X-App-Rate-Limit").get(0));
            apiKeyRateLimits.setMethodRateLimitCount(resp.getHeaders().get("X-Method-Rate-Limit-Count").get(0));
            apiKeyRateLimits.setMethodRateLimitMax(resp.getHeaders().get("X-Method-Rate-Limit").get(0));

            this.apiKeyRateLimitsRepository.save(apiKeyRateLimits);


        } catch (HttpClientErrorException e) {
            Integer statusCode = e.getStatusCode().value();
            if (statusCode == 403) {
                this.apiKeyManager.banKey(apiKey, platform);
            }

            ApiKeyRateLimits apiKeyRateLimits = apiKey.getApiKeyRateLimits();
            /* Check to ban wether METHOD (endpoint) or APPLICATION (api key). Method will be implemented only if needed due to it complexibility. */
            if (e.getResponseHeaders().get("Retry-After") != null) {
                this.logger.warning(String.format("Api key rate limit reason: %s", e.getResponseHeaders().get("X-Rate-Limit-Type").get(0)));
                apiKeyRateLimits.setNextRetry(LocalDateTime.now().plusSeconds(Long.parseLong(e.getResponseHeaders().get("Retry-After").get(0))));
            } else {
                apiKeyRateLimits.setNextRetry(null);
            }

            this.apiKeyRateLimitsRepository.save(apiKeyRateLimits);
            this.logger.error(String.format("REST consumer status code [%s] with error %s", statusCode, e.getResponseBodyAsString()));
        } catch (HttpServerErrorException e) {
            this.logger.error(String.format("Riot api struggling. REST consumer status code [%s] with error %s", e.getStatusCode().value(), e.getResponseBodyAsString()));
        }
        return resp;
    }

    public ApiCall get(ApiCall apiCall) {
        ApiKey apiKey = null;
        if (apiCall.getApiEndpoint().isRequiresApiKey()) {
            apiKey = this.apiKeyManager.getKey(apiCall.getPlatform(), apiCall.getRiotGame());
        }
        Long startTime = System.currentTimeMillis();
        apiCall.setApiKey(apiKey);
        apiCall.setCallType(Constants.CALL_TYPE.GET);
        ResponseEntity<String> resp = this.call(apiCall.getApiEndpoint(), apiKey, apiCall.getPlatform(), HttpMethod.GET, null);
        apiCall.setJson(resp.getBody());
        apiCall.setResponseCode(resp.getStatusCodeValue());
        Long currentTime = System.currentTimeMillis();
        apiCall.setElapsedMilliseconds((int) (currentTime - startTime));
        this.apiCallRepository.save(apiCall);
        return apiCall;
    }

    public ApiCall post(ApiCall apiCall) {
        return this.post(apiCall, null);
    }

    public ApiCall post(ApiCall apiCall, Object body) {
        ApiKey apiKey = null;
        if (apiCall.getApiEndpoint().isRequiresApiKey()) {
            apiKey = this.apiKeyManager.getKey(apiCall.getPlatform(), apiCall.getRiotGame());
        }
        Long startTime = System.currentTimeMillis();
        apiCall.setApiKey(apiKey);
        apiCall.setCallType(Constants.CALL_TYPE.POST);
        ResponseEntity<String> resp = this.call(apiCall.getApiEndpoint(), apiKey, apiCall.getPlatform(), HttpMethod.POST, body);
        apiCall.setJson(resp.getBody());
        apiCall.setResponseCode(resp.getStatusCodeValue());
        Long currentTime = System.currentTimeMillis();
        apiCall.setElapsedMilliseconds((int) (currentTime - startTime));
        this.apiCallRepository.save(apiCall);
        return apiCall;
    }

    public ApiCall put(ApiCall apiCall) {
        return this.put(apiCall, null);
    }

    public ApiCall put(ApiCall apiCall, Object body) {
        ApiKey apiKey = null;
        if (apiCall.getApiEndpoint().isRequiresApiKey()) {
            apiKey = this.apiKeyManager.getKey(apiCall.getPlatform(), apiCall.getRiotGame());
        }
        Long startTime = System.currentTimeMillis();
        apiCall.setApiKey(apiKey);
        apiCall.setCallType(Constants.CALL_TYPE.PUT);
        ResponseEntity<String> resp = this.call(apiCall.getApiEndpoint(), apiKey, apiCall.getPlatform(), HttpMethod.PUT, body);
        apiCall.setJson(resp.getBody());
        apiCall.setResponseCode(resp.getStatusCodeValue());
        Long currentTime = System.currentTimeMillis();
        apiCall.setElapsedMilliseconds((int) (currentTime - startTime));
        this.apiCallRepository.save(apiCall);
        return apiCall;
    }

    public ApiCall delete(ApiCall apiCall) {
        ApiKey apiKey = null;
        if (apiCall.getApiEndpoint().isRequiresApiKey()) {
            apiKey = this.apiKeyManager.getKey(apiCall.getPlatform(), apiCall.getRiotGame());
        }
        Long startTime = System.currentTimeMillis();
        apiCall.setApiKey(apiKey);
        apiCall.setCallType(Constants.CALL_TYPE.DELETE);
        ResponseEntity<String> resp = this.call(apiCall.getApiEndpoint(), apiKey, apiCall.getPlatform(), HttpMethod.DELETE, null);
        apiCall.setJson(resp.getBody());
        apiCall.setResponseCode(resp.getStatusCodeValue());
        Long currentTime = System.currentTimeMillis();
        apiCall.setElapsedMilliseconds((int) (currentTime - startTime));
        this.apiCallRepository.save(apiCall);
        return apiCall;
    }

}

