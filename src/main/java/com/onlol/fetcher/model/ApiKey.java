package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiKey {

    @Id
    @Column(nullable = false, unique = true)
    private String apiKey;

    @Column(nullable = false, unique = false)
    private Integer retryAfter = 0;

    @Column(nullable = false, unique = false)
    private boolean banned = false;

    @Column(nullable = false, unique = false)
    private boolean isValid = true;

    @Column(nullable = false, unique = false)
    private Long lastTimestampUsed;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Integer getRetryAfter() {
        return retryAfter;
    }

    public void setRetryAfter(Integer retryAfter) {
        this.retryAfter = retryAfter;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public Long getLastTimestampUsed() {
        return lastTimestampUsed;
    }

    public void setLastTimestampUsed(Long lastTimestampUsed) {
        this.lastTimestampUsed = lastTimestampUsed;
    }

    @Override
    public String toString() {
        return "ApiKey{" +
                "apiKey='" + apiKey + '\'' +
                ", retryAfter=" + retryAfter +
                ", banned=" + banned +
                ", isValid=" + isValid +
                ", lastTimestampUsed=" + lastTimestampUsed +
                '}';
    }
}
