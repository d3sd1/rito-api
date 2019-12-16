package com.global.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Platform> platforms;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<RiotGame> riotGames;

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

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
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

    public List<RiotGame> getRiotGames() {
        return riotGames;
    }

    public void setRiotGames(List<RiotGame> riotGames) {
        this.riotGames = riotGames;
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

    @Override
    public String toString() {
        return "ApiKey{" +
                "id=" + id +
                ", apiKey='" + apiKey + '\'' +
                ", platforms=" + platforms +
                ", riotGames=" + riotGames +
                ", apiKeyRateLimits=" + apiKeyRateLimits +
                ", configured=" + configured +
                ", disabled=" + disabled +
                ", lastTimeUsed=" + lastTimeUsed +
                '}';
    }
}
