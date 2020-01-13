/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * API Call model.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "logging")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne
    private ApiEndpoint apiEndpoint;

    @OneToOne
    private Platform platform;

    @OneToOne
    private ApiKey forcedApiKey;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable()
    @MapKeyColumn()
    @Column()
    private Map<String, String> parameters = new HashMap<String, String>();

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
     * Gets api endpoint.
     *
     * @return the api endpoint
     */
    public ApiEndpoint getApiEndpoint() {
        return apiEndpoint;
    }

    /**
     * Sets api endpoint.
     *
     * @param apiEndpoint the api endpoint
     */
    public void setApiEndpoint(ApiEndpoint apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    /**
     * Gets platform.
     *
     * @return the platform
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Sets platform.
     *
     * @param platform the platform
     */
    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    /**
     * Gets parameters.
     *
     * @return the parameters
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Sets parameters.
     *
     * @param parameters the parameters
     */
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public ApiKey getForcedApiKey() {
        return forcedApiKey;
    }

    public void setForcedApiKey(ApiKey forcedApiKey) {
        this.forcedApiKey = forcedApiKey;
    }
}
