package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameType {
    @Id
    @Column(nullable = false, unique = true)
    private String gametype;

    @Column(nullable = true, unique = false)
    private String description;

    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GameType{" +
                "gametype='" + gametype + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
