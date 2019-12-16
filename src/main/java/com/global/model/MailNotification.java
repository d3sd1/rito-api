package com.global.model;

import com.global.services.logger.Log;

import javax.persistence.*;


@Entity
@Table(schema = "logging")
public class MailNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;

    @OneToOne()
    private Log log;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Log getLog() {
        return log;
    }

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
