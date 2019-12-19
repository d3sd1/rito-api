/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.model;

import com.global.services.logger.Log;

import javax.persistence.*;


/**
 * Mail notification model.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "logging")
public class MailNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;

    @OneToOne()
    private Log log;

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
     * Gets log.
     *
     * @return the log
     */
    public Log getLog() {
        return log;
    }

    /**
     * Sets log.
     *
     * @param log the log
     */
    public void setLog(Log log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "MailNotification{" +
                "id=" + id +
                ", log=" + log +
                '}';
    }
}
