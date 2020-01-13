/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import global.model.ApiKey;
import com.onlol.deserializer.LeagueEntryDeserializer;

import javax.persistence.*;

/**
 * League Entry model.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "onlol")
@JsonDeserialize(using = LeagueEntryDeserializer.class)
public class SummonerIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;

    @OneToOne
    private Summoner summoner;

    @OneToOne
    private ApiKey apiKey;

    @Column(nullable = false, unique = true)
    private String summonerId;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets summoner.
     *
     * @return the summoner
     */
    public Summoner getSummoner() {
        return summoner;
    }

    /**
     * Sets summoner.
     *
     * @param summoner the summoner
     */
    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

    /**
     * Gets api key.
     *
     * @return the api key
     */
    public ApiKey getApiKey() {
        return apiKey;
    }

    /**
     * Sets api key.
     *
     * @param apiKey the api key
     */
    public void setApiKey(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Gets summoner id.
     *
     * @return the summoner id
     */
    public String getSummonerId() {
        return summonerId;
    }

    /**
     * Sets summoner id.
     *
     * @param summonerId the summoner id
     */
    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }
}
