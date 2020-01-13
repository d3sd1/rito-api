/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Api key model.
 *
 * @author d3sd1
 * @version 0.0.9
 */
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
     * Gets api key.
     *
     * @return the api key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sets api key.
     *
     * @param apiKey the api key
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Is configured boolean.
     *
     * @return the boolean
     */
    public boolean isConfigured() {
        return configured;
    }

    /**
     * Sets configured.
     *
     * @param configured the configured
     */
    public void setConfigured(boolean configured) {
        this.configured = configured;
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
     * Gets api key rate limits.
     *
     * @return the api key rate limits
     */
    public ApiKeyRateLimits getApiKeyRateLimits() {
        return apiKeyRateLimits;
    }

    /**
     * Sets api key rate limits.
     *
     * @param apiKeyRateLimits the api key rate limits
     */
    public void setApiKeyRateLimits(ApiKeyRateLimits apiKeyRateLimits) {
        this.apiKeyRateLimits = apiKeyRateLimits;
    }

    /**
     * Gets last time used.
     *
     * @return the last time used
     */
    public LocalDateTime getLastTimeUsed() {
        return lastTimeUsed;
    }

    /**
     * Sets last time used.
     *
     * @param lastTimeUsed the last time used
     */
    public void setLastTimeUsed(LocalDateTime lastTimeUsed) {
        this.lastTimeUsed = lastTimeUsed;
    }

    /**
     * Gets availability.
     *
     * @return the availability
     */
    public List<ApiKeyAvailability> getAvailability() {
        return availability;
    }

    /**
     * Sets availability.
     *
     * @param availability the availability
     */
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
