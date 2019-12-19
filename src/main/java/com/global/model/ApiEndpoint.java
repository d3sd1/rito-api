/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.model;

import javax.persistence.*;


/**
 * Api endpoint model.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "common")
public class ApiEndpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;

    @Column(nullable = false, unique = true)
    private String keyName;

    @Column(nullable = false, unique = false)
    private String endpoint;

    @Column(nullable = false, unique = false)
    private boolean disabled;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    @Column(nullable = false, unique = false)
    private ApiMethod method = ApiMethod.GET;

    @Column(nullable = false, unique = false)
    private boolean requiresApiKey;

    @OneToOne
    private RiotGame riotGame;

    @Column(nullable = false, unique = false)
    private boolean countsForRateLimit;

    @Column(nullable = true, unique = false)
    private String stub;

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets endpoint.
     *
     * @return the endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Sets endpoint.
     *
     * @param endpoint the endpoint
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Is disabled boolean.
     *
     * @return the boolean
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets disabled.
     *
     * @param disabled the disabled
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * Gets method.
     *
     * @return the method
     */
    public ApiMethod getMethod() {
        return method;
    }

    /**
     * Sets method.
     *
     * @param method the method
     */
    public void setMethod(ApiMethod method) {
        this.method = method;
    }

    /**
     * Is requires api key boolean.
     *
     * @return the boolean
     */
    public boolean isRequiresApiKey() {
        return requiresApiKey;
    }

    /**
     * Sets requires api key.
     *
     * @param requiresApiKey the requires api key
     */
    public void setRequiresApiKey(boolean requiresApiKey) {
        this.requiresApiKey = requiresApiKey;
    }

    /**
     * Gets riot game.
     *
     * @return the riot game
     */
    public RiotGame getRiotGame() {
        return riotGame;
    }

    /**
     * Sets riot game.
     *
     * @param riotGame the riot game
     */
    public void setRiotGame(RiotGame riotGame) {
        this.riotGame = riotGame;
    }

    /**
     * Is counts for rate limit boolean.
     *
     * @return the boolean
     */
    public boolean isCountsForRateLimit() {
        return countsForRateLimit;
    }

    /**
     * Sets counts for rate limit.
     *
     * @param countsForRateLimit the counts for rate limit
     */
    public void setCountsForRateLimit(boolean countsForRateLimit) {
        this.countsForRateLimit = countsForRateLimit;
    }

    /**
     * Gets stub url (testing-only url).
     *
     * @return the stub
     */
    public String getStub() {
        return stub;
    }

    /**
     * Sets stub url (testing-only url).
     *
     * @param stub the stub
     */
    public void setStub(String stub) {
        this.stub = stub;
    }

    /**
     * Gets key name.
     *
     * @return the key name
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     * Sets key name.
     *
     * @param keyName the key name
     */
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    /**
     * The enum Api method.
     */
    enum ApiMethod {
        /**
         * Get api method.
         */
        GET,
        /**
         * Post api method.
         */
        POST,
        /**
         * Put api method.
         */
        PUT,
        /**
         * Delete api method.
         */
        DELETE
    }

    @Override
    public String toString() {
        return "ApiEndpoint{" +
                "id=" + id +
                ", keyName='" + keyName + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", disabled=" + disabled +
                ", method=" + method +
                ", requiresApiKey=" + requiresApiKey +
                ", riotGame=" + riotGame +
                ", countsForRateLimit=" + countsForRateLimit +
                ", stub='" + stub + '\'' +
                '}';
    }
}
