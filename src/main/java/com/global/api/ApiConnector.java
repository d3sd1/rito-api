/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.api;

import com.global.configuration.Constants;
import com.global.model.*;
import com.global.repository.ApiCallRepository;
import com.global.repository.ApiKeyRateLimitsRepository;
import com.global.repository.ApiResponseRepository;
import com.global.services.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Api caller. Used to communicate with Riot Games Api.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Service
public class ApiConnector {

    @Value("${spring.profiles.active}")
    private String envName;

    private ApiKeyManager apiKeyManager;
    private ApiResponseRepository apiResponseRepository;
    private Logger logger;
    private ApiKeyRateLimitsRepository apiKeyRateLimitsRepository;
    private ApiCallRepository apiCallRepository;

    public ApiConnector(ApiKeyManager apiKeyManager,
                        ApiResponseRepository apiResponseRepository,
                        Logger logger,
                        ApiKeyRateLimitsRepository apiKeyRateLimitsRepository,
                        ApiCallRepository apiCallRepository) {
        this.apiKeyManager = apiKeyManager;
        this.apiResponseRepository = apiResponseRepository;
        this.logger = logger;
        this.apiKeyRateLimitsRepository = apiKeyRateLimitsRepository;
        this.apiCallRepository = apiCallRepository;
    }

    /**
     * Internal call for this class. Used to communicate scraper with remote apis.
     *
     * @param apiEndpoint The endpoint to trigger.
     * @param apiKey      The apiKey selected to use.
     * @param platform    The platform of the object call.
     * @param httpMethod  Call type (GET, POST, PUT, DELETE)
     * @param parameters  Parameters to replace on the uri.
     * @param body        Body to use on selected calls (POST, PUT)-
     * @return the api response.
     * @author d3sd1
     */
    private ResponseEntity<String> call(ApiEndpoint apiEndpoint, ApiKey apiKey, Platform platform, HttpMethod httpMethod,
                                        Map<String, String> parameters, Object body) {
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
            String envUrl = apiEndpoint.getEndpoint();
            if (this.envName.equalsIgnoreCase("dev") && apiEndpoint.getStub() != null) {
                envUrl = apiEndpoint.getStub();
            }
            String finalUrl = envUrl.replace("{{hostUrl}}", platform.getHostName());
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                finalUrl = finalUrl.replace(String.format("{{%s}}", key), value);
            }
            resp = restTemplate.exchange(
                    finalUrl,
                    httpMethod, requestEntity,
                    String.class);
            /* Fill Api Key params */
            ApiKeyRateLimits apiKeyRateLimits = apiKey.getApiKeyRateLimits();
            apiKeyRateLimits.setAppRateLimitCount(resp.getHeaders().get("X-App-Rate-Limit-Count").get(0));
            apiKeyRateLimits.setAppRateLimitMax(resp.getHeaders().get("X-App-Rate-Limit").get(0));
            apiKeyRateLimits.setMethodRateLimitCount(resp.getHeaders().get("X-Method-Rate-Limit-Count").get(0));
            apiKeyRateLimits.setMethodRateLimitMax(resp.getHeaders().get("X-Method-Rate-Limit").get(0));

            this.apiKeyRateLimitsRepository.save(apiKeyRateLimits);
        } catch (IllegalArgumentException e) {
            this.logger.error(String.format("Api call failured due to misconfiguration: Endpoint [%s] with parameter error [%s]. Parameters provided: %s", apiEndpoint.getEndpoint(), e.getLocalizedMessage(), parameters));
        } catch (HttpClientErrorException e) {
            Integer statusCode = e.getStatusCode().value();
            if (statusCode == 403) {
                this.apiKeyManager.banKey(apiKey, platform, apiEndpoint.getRiotGame());
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

    /**
     * GET type api call.
     *
     * @param apiCall the api call
     * @return the api response
     * @author d3sd1
     */
    public ApiResponse get(ApiCall apiCall) {
        ApiKey apiKey = null;
        ApiResponse apiResponse = new ApiResponse();

        if (apiCall.getApiEndpoint().isRequiresApiKey()) {
            apiKey = this.apiKeyManager.getKey(apiCall.getPlatform(), apiCall.getRiotGame());
        }
        Long startTime = System.currentTimeMillis();
        apiResponse.setApiKey(apiKey);
        apiResponse.setCallType(Constants.CALL_TYPE.GET);
        ResponseEntity<String> resp = this.call(apiCall.getApiEndpoint(), apiKey, apiCall.getPlatform(), HttpMethod.GET,
                apiCall.getParameters(), null);
        apiResponse.setJson(resp.getBody());
        apiResponse.setResponseCode(resp.getStatusCodeValue());
        Long currentTime = System.currentTimeMillis();
        apiResponse.setElapsedMilliseconds((int) (currentTime - startTime));
        this.apiCallRepository.save(apiCall);
        this.apiResponseRepository.save(apiResponse);
        return apiResponse;
    }

    /**
     * Post api call.
     *
     * @param apiCall the api call
     * @return the api call
     * @author d3sd1
     */
    public ApiResponse post(ApiCall apiCall) {
        return this.post(apiCall, null);
    }

    /**
     * POST type api call.
     *
     * @param apiCall the api call
     * @param body    the body
     * @return the api response
     * @author d3sd1
     */
    public ApiResponse post(ApiCall apiCall, Object body) {
        ApiKey apiKey = null;
        ApiResponse apiResponse = new ApiResponse();

        if (apiCall.getApiEndpoint().isRequiresApiKey()) {
            apiKey = this.apiKeyManager.getKey(apiCall.getPlatform(), apiCall.getRiotGame());
        }
        Long startTime = System.currentTimeMillis();
        apiResponse.setApiKey(apiKey);
        apiResponse.setCallType(Constants.CALL_TYPE.POST);
        ResponseEntity<String> resp = this.call(apiCall.getApiEndpoint(), apiKey, apiCall.getPlatform(), HttpMethod.POST,
                apiCall.getParameters(), body);
        apiResponse.setJson(resp.getBody());
        apiResponse.setResponseCode(resp.getStatusCodeValue());
        Long currentTime = System.currentTimeMillis();
        apiResponse.setElapsedMilliseconds((int) (currentTime - startTime));
        this.apiCallRepository.save(apiCall);
        this.apiResponseRepository.save(apiResponse);
        return apiResponse;
    }

    /**
     * PUT type api call without body.
     *
     * @param apiCall the api call
     * @return the api response
     * @author d3sd1
     */
    public ApiResponse put(ApiCall apiCall) {
        return this.put(apiCall, null);
    }

    /**
     * PUT type api call.
     *
     * @param apiCall the api call
     * @param body    the body
     * @return the api response
     * @author d3sd1
     */
    public ApiResponse put(ApiCall apiCall, Object body) {
        ApiKey apiKey = null;
        ApiResponse apiResponse = new ApiResponse();

        if (apiCall.getApiEndpoint().isRequiresApiKey()) {
            apiKey = this.apiKeyManager.getKey(apiCall.getPlatform(), apiCall.getRiotGame());
        }
        Long startTime = System.currentTimeMillis();
        apiResponse.setApiKey(apiKey);
        apiResponse.setCallType(Constants.CALL_TYPE.PUT);
        ResponseEntity<String> resp = this.call(apiCall.getApiEndpoint(), apiKey, apiCall.getPlatform(), HttpMethod.PUT,
                apiCall.getParameters(), body);
        apiResponse.setJson(resp.getBody());
        apiResponse.setResponseCode(resp.getStatusCodeValue());
        Long currentTime = System.currentTimeMillis();
        apiResponse.setElapsedMilliseconds((int) (currentTime - startTime));
        this.apiCallRepository.save(apiCall);
        this.apiResponseRepository.save(apiResponse);
        return apiResponse;
    }

    /**
     * DELETE type api call.
     *
     * @param apiCall the api call
     * @return the api response
     * @author d3sd1
     */
    public ApiResponse delete(ApiCall apiCall) {
        ApiKey apiKey = null;
        ApiResponse apiResponse = new ApiResponse();

        if (apiCall.getApiEndpoint().isRequiresApiKey()) {
            apiKey = this.apiKeyManager.getKey(apiCall.getPlatform(), apiCall.getRiotGame());
        }
        Long startTime = System.currentTimeMillis();
        apiResponse.setApiKey(apiKey);
        apiResponse.setCallType(Constants.CALL_TYPE.DELETE);
        ResponseEntity<String> resp = this.call(apiCall.getApiEndpoint(), apiKey, apiCall.getPlatform(), HttpMethod.DELETE,
                apiCall.getParameters(), null);
        apiResponse.setJson(resp.getBody());
        apiResponse.setResponseCode(resp.getStatusCodeValue());
        Long currentTime = System.currentTimeMillis();
        apiResponse.setElapsedMilliseconds((int) (currentTime - startTime));
        this.apiCallRepository.save(apiCall);
        this.apiResponseRepository.save(apiResponse);
        return apiResponse;
    }

}

