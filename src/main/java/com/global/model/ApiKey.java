package com.global.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//prueba
@Entity
@Table(schema = "common")
@JsonIgnoreProperties(ignoreUnknown = true)
@Transactional
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String apiKey;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ApiKeyAvailability> availability = new ArrayList<>();

    @OneToOne
    private ApiKeyRateLimits apiKeyRateLimits = new ApiKeyRateLimits();

    @Column(nullable = false, unique = false)
    private boolean configured = false;

    @Column(nullable = false, unique = false)
    private boolean disabled = false;

    @Column(nullable = true, unique = false)
    private LocalDateTime lastTimeUsed;


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

    public boolean isConfigured() {
        return configured;
    }

    public void setConfigured(boolean configured) {
        this.configured = configured;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public ApiKeyRateLimits getApiKeyRateLimits() {
        return apiKeyRateLimits;
    }

    public void setApiKeyRateLimits(ApiKeyRateLimits apiKeyRateLimits) {
        this.apiKeyRateLimits = apiKeyRateLimits;
    }

    public LocalDateTime getLastTimeUsed() {
        return lastTimeUsed;
    }

    public void setLastTimeUsed(LocalDateTime lastTimeUsed) {
        this.lastTimeUsed = lastTimeUsed;
    }

    public List<ApiKeyAvailability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<ApiKeyAvailability> availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "ApiKey{" +
                "id=" + id +
                ", apiKey='" + apiKey + '\'' +
                ", availability=" + availability +
                ", apiKeyRateLimits=" + apiKeyRateLimits +
                ", configured=" + configured +
                ", disabled=" + disabled +
                ", lastTimeUsed=" + lastTimeUsed +
                '}';
    }
}
