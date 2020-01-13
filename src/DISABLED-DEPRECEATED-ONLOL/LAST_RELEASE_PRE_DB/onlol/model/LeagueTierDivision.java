/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.onlol.deserializer.LeagueEntryDeserializer;

import javax.persistence.*;

/**
 * League Tier Division model.
 *
 * @author d3sd1
 * @version 1.0.5
 */
@Entity
@Table(schema = "onlol")
@JsonDeserialize(using = LeagueEntryDeserializer.class)
public class LeagueTierDivision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private short id;

    @OneToOne
    private LeagueTier leagueTier;

    @OneToOne
    private LeagueDivision leagueDivision;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public LeagueTier getLeagueTier() {
        return leagueTier;
    }

    public void setLeagueTier(LeagueTier leagueTier) {
        this.leagueTier = leagueTier;
    }

    public LeagueDivision getLeagueDivision() {
        return leagueDivision;
    }

    public void setLeagueDivision(LeagueDivision leagueDivision) {
        this.leagueDivision = leagueDivision;
    }

    @Override
    public String toString() {
        return "LeagueTierDivision{" +
                "id=" + id +
                ", leagueTier=" + leagueTier +
                ", leagueDivision=" + leagueDivision +
                '}';
    }
}
