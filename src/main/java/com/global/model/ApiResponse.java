/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.global.configuration.Constants;

import javax.persistence.*;

/**
 * Api response model.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "logging")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = true, unique = false, columnDefinition = "text")
    private String json;

    @OneToOne
    private ApiKey apiKey;

    @Column(nullable = true, unique = false)
    private Constants.CALL_TYPE callType = Constants.CALL_TYPE.GET;

    @Column(nullable = false, unique = false, columnDefinition = "text")
    private Integer elapsedMilliseconds;

    @Column(nullable = false, unique = false)
    private Integer responseCode;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets json.
     *
     * @return the json
     */
    public String getJson() {
        return json;
    }

    /**
     * Sets json.
     *
     * @param json the json
     */
    public void setJson(String json) {
        this.json = json;
    }

    /**
     * Gets api key.
     *
     * @return the api key
     */
    public ApiKey getApiKey() {
        return apiKey;
    }

    /**
     * Sets api key.
     *
     * @param apiKey the api key
     */
    public void setApiKey(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Gets call type.
     *
     * @return the call type
     */
    public Constants.CALL_TYPE getCallType() {
        return callType;
    }

    /**
     * Sets call type.
     *
     * @param callType the call type
     */
    public void setCallType(Constants.CALL_TYPE callType) {
        this.callType = callType;
    }

    /**
     * Gets elapsed milliseconds.
     *
     * @return the elapsed milliseconds
     */
    public Integer getElapsedMilliseconds() {
        return elapsedMilliseconds;
    }

    /**
     * Sets elapsed milliseconds.
     *
     * @param elapsedMilliseconds the elapsed milliseconds
     */
    public void setElapsedMilliseconds(Integer elapsedMilliseconds) {
        this.elapsedMilliseconds = elapsedMilliseconds;
    }

    /**
     * Gets response code.
     *
     * @return the response code
     */
    public Integer getResponseCode() {
        return responseCode;
    }

    /**
     * Sets response code.
     *
     * @param responseCode the response code
     */
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "id=" + id +
                ", json='" + json + '\'' +
                ", apiKey=" + apiKey +
                ", callType=" + callType +
                ", elapsedMilliseconds=" + elapsedMilliseconds +
                ", responseCode=" + responseCode +
                '}';
    }
}
