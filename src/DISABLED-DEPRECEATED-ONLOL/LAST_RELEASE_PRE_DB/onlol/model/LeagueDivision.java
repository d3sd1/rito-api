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
 * League Entry model.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "onlol")
@JsonDeserialize(using = LeagueEntryDeserializer.class)
public class LeagueDivision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private short id;

    @Column(nullable = false, unique = true)
    private String keyName;

    /**
     * Gets id.
     *
     * @return the id
     */
    public short getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(short id) {
        this.id = id;
    }

    /**
     * Gets key name.
     *
     * @return the key name
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     * Sets key name.
     *
     * @param keyName the key name
     */
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public String toString() {
        return "LeagueDivision{" +
                "id=" + id +
                ", keyName='" + keyName + '\'' +
                '}';
    }
}
