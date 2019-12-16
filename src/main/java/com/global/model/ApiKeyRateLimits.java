package com.global.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppRateLimitMax() {
        return appRateLimitMax;
    }

    public void setAppRateLimitMax(String appRateLimitMax) {
        this.appRateLimitMax = appRateLimitMax;
    }

    public LocalDateTime getNextRetry() {
        return nextRetry;
    }

    public void setNextRetry(LocalDateTime nextRetry) {
        this.nextRetry = nextRetry;
    }

    public String getAppRateLimitCount() {
        return appRateLimitCount;
    }

    public void setAppRateLimitCount(String appRateLimitCount) {
        this.appRateLimitCount = appRateLimitCount;
    }

    public String getMethodRateLimitMax() {
        return methodRateLimitMax;
    }

    public void setMethodRateLimitMax(String methodRateLimitMax) {
        this.methodRateLimitMax = methodRateLimitMax;
    }

    public String getMethodRateLimitCount() {
        return methodRateLimitCount;
    }

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
