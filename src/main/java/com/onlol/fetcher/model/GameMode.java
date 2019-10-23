package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameMode {
    @Id
    @Column(nullable = false, unique = true)
    private String gameMode;

    @Column(nullable = true, unique = false)
    private String description;

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GameMode{" +
                "gameMode='" + gameMode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
