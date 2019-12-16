package com.global.api;

import com.global.configuration.Constants;
import com.global.model.ApiCall;
import com.global.model.ApiEndpoint;
import com.global.model.ApiKey;
import com.global.repository.ApiCallRepository;
import com.global.repository.ApiKeyRepository;
import com.global.services.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiConnector {

    @Autowired
    private ApiKeyManager apiKeyManager;

    @Autowired
    private Logger logger;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private ApiCallRepository apiCallRepository;

    private ResponseEntity<String> call(ApiEndpoint apiEndpoint, ApiKey apiKey, HttpMethod httpMethod, Object body) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity requestEntity = null;
        if (apiEndpoint.isRequiresApiKey()) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Riot-Token", apiKey.getApiKey());
            requestEntity = new HttpEntity<>(body, headers);
        } else {
            requestEntity = new HttpEntity<>(body);
        }
        ResponseEntity<String> resp = new ResponseEntity<String>(HttpStatus.SEE_OTHER);
        try {
            resp = restTemplate.exchange(
                    apiEndpoint.getEndpoint(),
                    httpMethod, requestEntity,
                    String.class);
        } catch (HttpClientErrorException e) {
            this.logger.error(String.format("REST consumer status code [%s] with error %s", e.getStatusCode().value(), e.getResponseBodyAsString()));
        } catch (HttpServerErrorException e) {
            this.logger.error(String.format("Riot api struggling. REST consumer status code [%s] with error %s", e.getStatusCode().value(), e.getResponseBodyAsString()));
        }
        return resp;
    }

    public ApiCall get(ApiEndpoint apiEndpoint) {
        return this.get(apiEndpoint, null);
    }

    public ApiCall get(ApiEndpoint apiEndpoint, ApiKey apiKey) {
        Long startTime = System.currentTimeMillis();
        ApiCall apiCall = new ApiCall();
        apiCall.setApiKey(null);
        apiCall.setCallType(Constants.CALL_TYPE.GET);
        ResponseEntity<String> resp = this.call(apiEndpoint, apiKey, HttpMethod.GET, null);
        apiCall.setJson(resp.getBody());
        apiCall.setResponseCode(resp.getStatusCodeValue());
        Long currentTime = System.currentTimeMillis();
        apiCall.setElapsedMilliseconds((int) (currentTime - startTime));
        this.apiCallRepository.save(apiCall);
        return apiCall;
    }

    public ApiCall post(ApiEndpoint apiEndpoint) {
        return this.post(apiEndpoint, null);
    }


    public ApiCall post(ApiEndpoint apiEndpoint, Object body) {
        return this.post(apiEndpoint, null, body);
    }

    public ApiCall post(ApiEndpoint apiEndpoint, ApiKey apiKey, Object body) {
        Long startTime = System.currentTimeMillis();
        ApiCall apiCall = new ApiCall();
        apiCall.setApiKey(null);
        apiCall.setCallType(Constants.CALL_TYPE.POST);
        ResponseEntity<String> resp = this.call(apiEndpoint, apiKey, HttpMethod.POST, body);
        apiCall.setJson(resp.getBody());
        apiCall.setResponseCode(resp.getStatusCodeValue());
        Long currentTime = System.currentTimeMillis();
        apiCall.setElapsedMilliseconds((int) (currentTime - startTime));
        this.apiCallRepository.save(apiCall);
        return apiCall;
    }

    public ApiCall put(ApiEndpoint apiEndpoint) {
        return this.put(apiEndpoint, null);
    }

    public ApiCall put(ApiEndpoint apiEndpoint, Object body) {
        return this.put(apiEndpoint, null, body);
    }

    public ApiCall put(ApiEndpoint apiEndpoint, ApiKey apiKey, Object body) {
        Long startTime = System.currentTimeMillis();
        ApiCall apiCall = new ApiCall();
        apiCall.setApiKey(null);
        apiCall.setCallType(Constants.CALL_TYPE.PUT);
        ResponseEntity<String> resp = this.call(apiEndpoint, apiKey, HttpMethod.PUT, body);
        apiCall.setJson(resp.getBody());
        apiCall.setResponseCode(resp.getStatusCodeValue());
        Long currentTime = System.currentTimeMillis();
        apiCall.setElapsedMilliseconds((int) (currentTime - startTime));
        this.apiCallRepository.save(apiCall);
        return apiCall;
    }

    public ApiCall delete(ApiEndpoint apiEndpoint) {
        return this.delete(apiEndpoint, null);
    }

    public ApiCall delete(ApiEndpoint apiEndpoint, ApiKey apiKey) {
        Long startTime = System.currentTimeMillis();
        ApiCall apiCall = new ApiCall();
        apiCall.setApiKey(null);
        apiCall.setCallType(Constants.CALL_TYPE.DELETE);
        ResponseEntity<String> resp = this.call(apiEndpoint, apiKey, HttpMethod.DELETE, null);
        apiCall.setJson(resp.getBody());
        apiCall.setResponseCode(resp.getStatusCodeValue());
        Long currentTime = System.currentTimeMillis();
        apiCall.setElapsedMilliseconds((int) (currentTime - startTime));
        this.apiCallRepository.save(apiCall);
        return apiCall;
    }

}

