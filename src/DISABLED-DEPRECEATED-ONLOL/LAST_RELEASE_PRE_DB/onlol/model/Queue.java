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
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private short id;

    @Column(nullable = false, unique = true)
    private String keyName;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String tier) {
        this.keyName = tier;
    }

    @Override
    public String toString() {
        return "LeagueTier{" +
                "id=" + id +
                ", tier='" + keyName + '\'' +
                '}';
    }
}
