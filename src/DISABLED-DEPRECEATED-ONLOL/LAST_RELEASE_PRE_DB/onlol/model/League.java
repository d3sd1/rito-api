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
import com.onlol.deserializer.LeagueDeserializer;

import javax.persistence.*;

/**
 * League Entry model.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "onlol")
@JsonDeserialize(using = LeagueDeserializer.class)
public class League extends Scraper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(unique = true, nullable = false)
    private String riotId;

    @OneToOne(orphanRemoval = false)
    private com.onlol.model.LeagueTier leagueTier;

    @OneToOne(orphanRemoval = false)
    private com.onlol.model.LeagueDivision leagueDivision;

    @OneToOne(orphanRemoval = false)
    private com.onlol.model.Queue queue;

    @OneToOne(orphanRemoval = false)
    private Platform platform;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRiotId() {
        return riotId;
    }

    public void setRiotId(String riotId) {
        this.riotId = riotId;
    }

    public com.onlol.model.LeagueTier getLeagueTier() {
        return leagueTier;
    }

    public void setLeagueTier(com.onlol.model.LeagueTier leagueTier) {
        this.leagueTier = leagueTier;
    }

    public com.onlol.model.LeagueDivision getLeagueDivision() {
        return leagueDivision;
    }

    public void setLeagueDivision(com.onlol.model.LeagueDivision leagueDivision) {
        this.leagueDivision = leagueDivision;
    }

    public com.onlol.model.Queue getQueue() {
        return queue;
    }

    public void setQueue(com.onlol.model.Queue queue) {
        this.queue = queue;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "League{" +
                "id=" + id +
                ", riotId='" + riotId + '\'' +
                ", leagueTier=" + leagueTier +
                ", leagueDivision=" + leagueDivision +
                ", queue=" + queue +
                ", platform=" + platform +
                '}';
    }
}
