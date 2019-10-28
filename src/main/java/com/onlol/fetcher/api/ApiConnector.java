package com.onlol.fetcher.api;

import com.onlol.fetcher.exceptions.ApiBadRequestException;
import com.onlol.fetcher.exceptions.ApiDownException;
import com.onlol.fetcher.exceptions.ApiUnauthorizedException;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.ApiCall;
import com.onlol.fetcher.model.ApiKey;
import com.onlol.fetcher.repository.ApiCallRepository;
import com.onlol.fetcher.repository.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
public class ApiConnector {
    @Autowired
    private ApiKeyManager apiKeyManager;

    @Autowired
    private LogService logService;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private ApiCallRepository apiCallRepository;

    public ApiCall get(String url) throws DataNotfoundException, ApiUnauthorizedException, ApiBadRequestException, ApiDownException {
        return this.get(url, false, Byte.decode("0"));
    }

    public ApiCall get(String url, boolean needsApiKey) throws DataNotfoundException, ApiUnauthorizedException, ApiBadRequestException, ApiDownException {
        return this.get(url, needsApiKey, Byte.decode("0"));
    }

    public ApiCall get(String url, boolean needsApiKey, byte attempts) throws DataNotfoundException, ApiBadRequestException, ApiUnauthorizedException, ApiDownException {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity requestEntity = null;
        ApiKey apiKey = null;
        if (needsApiKey) {
            apiKey = this.apiKeyManager.getKey();
            if (apiKey == null) {
                // Enqueue after 60s if no api keys present
                this.logService.error("No api keys found. Sleeping 60 seconds.");
                try {
                    TimeUnit.SECONDS.sleep(60);
                } catch (InterruptedException e) {
                }
                return this.get(url, needsApiKey, ++attempts);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Riot-Token", apiKey.getApiKey());
            requestEntity = new HttpEntity(headers);
        }
        ResponseEntity<String> resp = null;
        try {
            resp = restTemplate.exchange(
                    url,
                    HttpMethod.GET, requestEntity,
                    String.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            switch (e.getStatusCode().value()) {
                case 400:
                    /*
                    Common Reasons:
                    A provided parameter is in the wrong format (e.g., a string instead of an integer).
                    A provided parameter is invalid (e.g., beginTime and startTime specify a time range that is too large).
                    A required parameter was not provided.
                     */
                    this.logService.error("ACTION REQUIRED. Malformed URL has thrown a 400 BAD REQUEST CODE. " + url + " with exception " + e.getMessage());
                    throw new ApiBadRequestException();
                case 401:
                    /*
                    Common Reasons:
                    An API key has not been included in the request.
                     */
                    if (needsApiKey) {
                        apiKey.setBanned(true);
                        apiKey.setValid(false);
                        this.apiKeyRepository.save(apiKey);
                    }
                    this.logService.error("ACTION REQUIRED. Unauthorized URL has thrown a 401 UNAUTHORIZED CODE. " + url + " with exception " + e.getMessage());
                    throw new ApiUnauthorizedException();
                case 403:
                    /*
                    Common Reasons:
                    An invalid API key was provided with the API request.
                    A blacklisted API key was provided with the API request.
                    The API request was for an incorrect or unsupported path.
                     */
                    this.logService.info("Forbidden URL: " + url);
                    throw new DataNotfoundException();
                case 404:
                    /*
                    Common Reasons:
                    The ID or name provided does not match any existing resource (e.g., there is no Summoner matching the specified ID).
                    There are no resources that match the parameters specified.
                     */
                    this.logService.info("Unknown url: " + url);
                    throw new DataNotfoundException();


                case 429:
                    /*
                    Common Reasons:
                    Unregulated API calls.
                     */
                    if (needsApiKey) {
                        apiKey.setBanned(true);
                        apiKey.setValid(true);
                        this.apiKeyRepository.save(apiKey);
                    }
                    return this.get(url, needsApiKey, ++attempts);

            }
        } catch (HttpServerErrorException e) {
            switch (e.getStatusCode().value()) {
                case 500:
                    this.logService.warning("Api struggling with 500 on " + url + " with exception " + e.getMessage());
                    throw new ApiDownException();
                case 503:
                    this.sleepGet(url, needsApiKey, attempts, e);

            }
        } catch (ResourceAccessException e) {
            this.sleepGet(url, needsApiKey, attempts, e);
        }
        ApiCall apiCall = new ApiCall(apiKey, resp.getBody());
        this.apiCallRepository.save(apiCall);
        return apiCall;
    }

    public ApiCall sleepGet(String url, boolean needsApiKey, Byte attempts, Exception e) throws ApiDownException, ApiUnauthorizedException, DataNotfoundException, ApiBadRequestException {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        // Prevent cycle. Retry only 3 times just in case.
        if (attempts < 3) {
            this.logService.warning("Api seems to be down on endpoint (503) " + url + " with exception " + e.getMessage());
            return this.get(url, needsApiKey, ++attempts);
        } else {
            throw new ApiDownException();
        }
    }

    public void post() {
        /*
        Tener en cuenta este back code :)
        415 (Unsupported Media Type) This error indicates that the server is refusing to service the request because the body of the request is in a format that is not supported.

Common Reasons

The Content-Type header was not appropriately set.
         */
    }

}

