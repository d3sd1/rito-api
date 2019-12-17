package com.global.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Table(schema = "common")
@JsonIgnoreProperties(ignoreUnknown = true)
@Transactional
public class ApiKeyAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @OneToOne
    private Platform platform;

    @OneToOne
    private RiotGame riotGame;

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RiotGame getRiotGame() {
        return riotGame;
    }

    public void setRiotGame(RiotGame riotGame) {
        this.riotGame = riotGame;
    }

    @Override
    public String toString() {
        return "ApiKeyAvailability{" +
                "id=" + id +
                ", platform=" + platform +
                ", riotGame=" + riotGame +
                '}';
    }
}
