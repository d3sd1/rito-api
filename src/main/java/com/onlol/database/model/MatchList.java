package com.onlol.database.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id; // YEMPORAL

    @OneToOne
    private GameLane gameLane; // lane string

    @OneToOne
    private MatchGame match; // gameId long

    @OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private Summoner summoner;

    @OneToOne
    private Champion champ; // champion int

    @OneToOne
    private GameSeason gameSeason; // season int

    @OneToOne
    private GameQueue gameQueue; // season int

    @OneToOne
    private GameRole gameRole; // role string

    @Column(nullable = true, unique = false)
    private LocalDateTime timestamp; // timestamp long

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameLane getGameLane() {
        return gameLane;
    }

    public void setGameLane(GameLane gameLane) {
        this.gameLane = gameLane;
    }

    public MatchGame getMatch() {
        return match;
    }

    public void setMatch(MatchGame match) {
        this.match = match;
    }

    public Summoner getSummoner() {
        return summoner;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

    public Champion getChamp() {
        return champ;
    }

    public void setChamp(Champion champ) {
        this.champ = champ;
    }

    public GameSeason getGameSeason() {
        return gameSeason;
    }

    public void setGameSeason(GameSeason gameSeason) {
        this.gameSeason = gameSeason;
    }

    public GameQueue getGameQueue() {
        return gameQueue;
    }

    public void setGameQueue(GameQueue gameQueue) {
        this.gameQueue = gameQueue;
    }

    public GameRole getGameRole() {
        return gameRole;
    }

    public void setGameRole(GameRole gameRole) {
        this.gameRole = gameRole;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MatchList{" +
                "id=" + id +
                ", lane=" + gameLane +
                ", match=" + match +
                ", summoner=" + summoner +
                ", champ=" + champ +
                ", season=" + gameSeason +
                ", queue=" + gameQueue +
                ", role=" + gameRole +
                ", timestamp=" + timestamp +
                '}';
    }
}
