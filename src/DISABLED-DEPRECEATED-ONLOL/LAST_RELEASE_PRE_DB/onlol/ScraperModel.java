/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import global.api.ApiConnector;
import global.model.ApiCall;
import global.model.ApiEndpoint;
import global.model.ApiKey;
import global.model.ApiResponse;
import global.model.Platform;
import global.scraper.Scraper;
import global.services.Logger;
import global.model.superclass.Scraper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class ScraperModel<T extends Scraper, K> {
    private Logger logger;
    private ApiConnector apiConnector;
    protected ObjectMapper objectMapper;
    protected Optional<T> object;
    private final Type type;
    private ApiKey forcedApiKey = null;


    public ScraperModel(Logger logger, ApiConnector apiConnector, ObjectMapper objectMapper) {
        this.logger = logger;
        this.apiConnector = apiConnector;
        this.objectMapper = objectMapper;

        // Get reflection class at runtime (needed for jackson).
        // Argument 1 means K, 0 would equal T =)
        Type mySuperclass = this.getClass().getGenericSuperclass();
        this.type = ((ParameterizedType) mySuperclass).getActualTypeArguments()[1];
    }

    protected abstract boolean canJoin(Optional<T> optional);

    protected abstract Optional<T> getObject();

    protected abstract ApiEndpoint getApiEndpoint();

    protected abstract void beforeApiCall(T obj);

    protected abstract void afterApiCall(T obj, K resp);

    protected abstract Platform getPlatform(T obj);

    protected abstract HashMap<String, String> getApiCallParameters(T obj);

    protected abstract boolean handleApiResponse(ApiResponse apiResponse);

    protected abstract HashMap<String, Object> getDeserializationInjectors(T obj, ApiResponse apiResponse);

    protected void setForcedApiKey(ApiKey apiKey) { // Forced api key
        this.forcedApiKey = apiKey;
    }

    //TODO: proccess priority queue too. (para cuando se requiera recarga inmediata). scrapers con recarga inmediata en general.
    //TODO: log messages...
    @Scraper
    protected void run() {
        //this.logger.info("Running!");
        Optional<T> optObj = this.getObject();
        if (!this.canJoin(optObj)) {
            this.logger.info("Not authorized"); // nothing to process
            return;
        }
        T obj = optObj.orElse(null);
        if (obj == null) {
            this.logger.error("Impossible to proccess operation. Object is null.");
            return;
        }
        obj.setExecuting(true);
        this.beforeApiCall(obj);
        K res = this.makeApiCall(obj, this.getApiEndpoint(), this.getApiCallParameters(obj));
        obj.setExecuting(false);
        obj.setLastExecTime(System.currentTimeMillis() / 1000);
        this.afterApiCall(obj, res);
    }

    private K makeApiCall(T obj, ApiEndpoint apiEndpoint, HashMap<String, String> params) {

        this.logger.info(String.format("Executing scraper[%s] (%s) with params",
                this.getClass().getSimpleName(), params));
        ApiCall apiCall = new ApiCall();
        apiCall.setApiEndpoint(apiEndpoint);
        apiCall.setPlatform(this.getPlatform(obj));
        apiCall.setParameters(this.getApiCallParameters(obj));
        try {
            ApiResponse apiResponse = this.apiConnector.get(apiCall, this.forcedApiKey);

            boolean canContinue = this.handleApiResponse(apiResponse);
            if (!canContinue) {
                return null;
            }

            if (apiResponse.getJson() != null) {
                InjectableValues.Std injectable = new InjectableValues.Std();
                for (Map.Entry<String, Object> entry : this.getDeserializationInjectors(obj, apiResponse).entrySet()) {
                    injectable.addValue(entry.getKey(), entry.getValue());
                }
                objectMapper.setInjectableValues(injectable);
                JavaType type = objectMapper.getTypeFactory().
                        constructType(this.type);

                final K entries = objectMapper.readValue(apiResponse.getJson(), type);
                return entries;
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error(e);
        }
        return null;
    }
}
