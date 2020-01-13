/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import global.model.Platform;
import global.model.superclass.Scraper;
import com.onlol.deserializer.SummonerDeserializer;

import javax.persistence.*;

/**
 * League Entry model.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "onlol")
@JsonDeserialize(using = SummonerDeserializer.class)
public class Summoner extends Scraper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;

    @Column(nullable = false, unique = false)
    private String name;

    @Column(nullable = false, unique = false)
    private int profileIconId;

    @Column(nullable = false, unique = false)
    private long revisionDate;

    @Column(nullable = false, unique = false)
    private int summonerLevel;

    @OneToOne(orphanRemoval = false)
    private Platform platform;

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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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

    public int getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    public long getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(long revisionDate) {
        this.revisionDate = revisionDate;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(int summonerLevel) {
        this.summonerLevel = summonerLevel;
    }
}
