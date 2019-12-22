/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.onlol.deserializer.LeagueEntryDeserializer;

import javax.persistence.*;

/**
 * API Call model.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "onlol")
@JsonDeserialize(using = LeagueEntryDeserializer.class)
public class LeagueEntryMiniSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column()
    private String progress = "";

    @Column()
    private short losses = 0;

    @Column()
    private short target = 0;

    @Column()
    private short wins = 0;

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
     * Gets progress.
     *
     * @return the progress
     */
    public String getProgress() {
        return progress;
    }

    /**
     * Sets progress.
     *
     * @param progress the progress
     */
    public void setProgress(String progress) {
        this.progress = progress;
    }

    /**
     * Gets losses.
     *
     * @return the losses
     */
    public short getLosses() {
        return losses;
    }

    /**
     * Sets losses.
     *
     * @param losses the losses
     */
    public void setLosses(short losses) {
        this.losses = losses;
    }

    /**
     * Gets target.
     *
     * @return the target
     */
    public short getTarget() {
        return target;
    }

    /**
     * Sets target.
     *
     * @param target the target
     */
    public void setTarget(short target) {
        this.target = target;
    }

    /**
     * Gets wins.
     *
     * @return the wins
     */
    public short getWins() {
        return wins;
    }

    /**
     * Sets wins.
     *
     * @param wins the wins
     */
    public void setWins(short wins) {
        this.wins = wins;
    }

    @Override
    public String toString() {
        return "LeagueEntryMiniSeries{" +
                "id=" + id +
                ", progress='" + progress + '\'' +
                ", losses=" + losses +
                ", target=" + target +
                ", wins=" + wins +
                '}';
    }
}
