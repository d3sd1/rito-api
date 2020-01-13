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
public class LeagueExp extends Scraper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne
    private Platform platform;

    @OneToOne
    private com.onlol.model.Queue queue;

    @OneToOne
    private com.onlol.model.LeagueTierDivision leagueTierDivision;

    @Column
    private int lastPage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public com.onlol.model.Queue getQueue() {
        return queue;
    }

    public void setQueue(com.onlol.model.Queue queue) {
        this.queue = queue;
    }

    public com.onlol.model.LeagueTierDivision getLeagueTierDivision() {
        return leagueTierDivision;
    }

    public void setLeagueTierDivision(com.onlol.model.LeagueTierDivision leagueTierDivision) {
        this.leagueTierDivision = leagueTierDivision;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    @Override
    public String toString() {
        return "LeagueExp{" +
                "id=" + id +
                ", platform=" + platform +
                ", queue=" + queue +
                ", leagueTierDivision=" + leagueTierDivision +
                ", lastPage=" + lastPage +
                '}';
    }
}
