/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Api key rate limit control model.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "common")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiKeyRateLimits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = false)
    private String appRateLimitMax = "0:0";

    @Column(nullable = true, unique = false)
    private LocalDateTime nextRetry;

    @Column(nullable = false, unique = false)
    private String appRateLimitCount = "0:0";

    @Column(nullable = false, unique = false)
    private String methodRateLimitMax = "0:0";

    @Column(nullable = false, unique = false)
    private String methodRateLimitCount = "0:0";

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets app rate limit max.
     *
     * @return the app rate limit max
     */
    public String getAppRateLimitMax() {
        return appRateLimitMax;
    }

    /**
     * Sets app rate limit max.
     *
     * @param appRateLimitMax the app rate limit max
     */
    public void setAppRateLimitMax(String appRateLimitMax) {
        this.appRateLimitMax = appRateLimitMax;
    }

    /**
     * Gets next retry.
     *
     * @return the next retry
     */
    public LocalDateTime getNextRetry() {
        return nextRetry;
    }

    /**
     * Sets next retry.
     *
     * @param nextRetry the next retry
     */
    public void setNextRetry(LocalDateTime nextRetry) {
        this.nextRetry = nextRetry;
    }

    /**
     * Gets app rate limit count.
     *
     * @return the app rate limit count
     */
    public String getAppRateLimitCount() {
        return appRateLimitCount;
    }

    /**
     * Sets app rate limit count.
     *
     * @param appRateLimitCount the app rate limit count
     */
    public void setAppRateLimitCount(String appRateLimitCount) {
        this.appRateLimitCount = appRateLimitCount;
    }

    /**
     * Gets method rate limit max.
     *
     * @return the method rate limit max
     */
    public String getMethodRateLimitMax() {
        return methodRateLimitMax;
    }

    /**
     * Sets method rate limit max.
     *
     * @param methodRateLimitMax the method rate limit max
     */
    public void setMethodRateLimitMax(String methodRateLimitMax) {
        this.methodRateLimitMax = methodRateLimitMax;
    }

    /**
     * Gets method rate limit count.
     *
     * @return the method rate limit count
     */
    public String getMethodRateLimitCount() {
        return methodRateLimitCount;
    }

    /**
     * Sets method rate limit count.
     *
     * @param methodRateLimitCount the method rate limit count
     */
    public void setMethodRateLimitCount(String methodRateLimitCount) {
        this.methodRateLimitCount = methodRateLimitCount;
    }

    @Override
    public String toString() {
        return "ApiKeyRateLimits{" +
                "id=" + id +
                ", appRateLimitMax='" + appRateLimitMax + '\'' +
                ", nextRetry=" + nextRetry +
                ", appRateLimitCount='" + appRateLimitCount + '\'' +
                ", methodRateLimitMax='" + methodRateLimitMax + '\'' +
                ", methodRateLimitCount='" + methodRateLimitCount + '\'' +
                '}';
    }
}
