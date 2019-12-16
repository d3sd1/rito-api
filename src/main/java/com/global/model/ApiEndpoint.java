package com.global.model;

import javax.persistence.*;


@Entity
@Table(schema = "common")
public class ApiEndpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;

    @Column(nullable = false, unique = false)
    private String endpoint;

    @Column(nullable = false, unique = false)
    private boolean disabled;

    enum ApiMethod
    {
        GET, POST, PUT, DELETE
    }

    @Column(nullable = false, unique = false)
    private ApiMethod method = ApiMethod.GET;

    @Column(nullable = false, unique = false)
    private boolean requiresApiKey;

    @OneToOne
    private RiotGame riotGame;

    @Column(nullable = false, unique = false)
    private boolean countsForRateLimit;

    // URL for the same endpoint on stub (test) mode. Used for tournaments.
    @Column(nullable = true, unique = false)
    private String stub;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public ApiMethod getMethod() {
        return method;
    }

    public void setMethod(ApiMethod method) {
        this.method = method;
    }

    public boolean isRequiresApiKey() {
        return requiresApiKey;
    }

    public void setRequiresApiKey(boolean requiresApiKey) {
        this.requiresApiKey = requiresApiKey;
    }

    public RiotGame getRiotGame() {
        return riotGame;
    }

    public void setRiotGame(RiotGame riotGame) {
        this.riotGame = riotGame;
    }

    public boolean isCountsForRateLimit() {
        return countsForRateLimit;
    }

    public void setCountsForRateLimit(boolean countsForRateLimit) {
        this.countsForRateLimit = countsForRateLimit;
    }

    public String getStub() {
        return stub;
    }

    public void setStub(String stub) {
        this.stub = stub;
    }

    @Override
    public String toString() {
        return "ApiEndpoint{" +
                "id=" + id +
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
