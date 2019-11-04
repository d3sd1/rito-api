package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String apiKey;

    @Column(nullable = true, unique = false)
    private Integer retryAfter = 0;

    @Column(nullable = false, unique = false)
    private boolean banned = false; // 429 err too  many requests handler

    @Column(nullable = false, unique = false)
    private boolean valid = true; // api key is not usable.

    @Column(nullable = false, unique = false)
    private Long lastTimestampUsed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
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
                "id=" + id +
                ", apiKey='" + apiKey + '\'' +
                ", retryAfter=" + retryAfter +
                ", banned=" + banned +
                ", valid=" + valid +
                ", lastTimestampUsed=" + lastTimestampUsed +
                '}';
    }
}
