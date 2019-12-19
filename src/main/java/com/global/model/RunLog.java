/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Run Log storage. Stores application runs.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class RunLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = false, columnDefinition = "text")
    private LocalDateTime runTime = LocalDateTime.now();

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets run time.
     *
     * @return the run time
     */
    public LocalDateTime getRunTime() {
        return runTime;
    }

    /**
     * Sets run time.
     *
     * @param runTime the run time
     */
    public void setRunTime(LocalDateTime runTime) {
        this.runTime = runTime;
    }

    @Override
    public String toString() {
        return "RunLog{" +
                "id=" + id +
                ", runTime=" + runTime +
                '}';
    }
}
