package com.onlol.fetcher.model;

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
    private Lane lane; // lane string

    @OneToOne
    private MatchGame match; // gameId long

    @OneToOne
    private Summoner summoner;

    @OneToOne
    private Champion champ; // champion int

    @OneToOne
    private Region region; // platformId string

    @OneToOne
    private Season season; // season int

    @OneToOne
    private GameQueue gameQueue; // season int

    @OneToOne
    private Role role; // role string

    @Column(nullable = true, unique = false)
    private LocalDateTime timestamp; // timestamp long

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lane getLane() {
        return lane;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public GameQueue getGameQueue() {
        return gameQueue;
    }

    public void setGameQueue(GameQueue gameQueue) {
        this.gameQueue = gameQueue;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
                ", lane=" + lane +
                ", match=" + match +
                ", summoner=" + summoner +
                ", champ=" + champ +
                ", region=" + region +
                ", season=" + season +
                ", queue=" + gameQueue +
                ", role=" + role +
                ", timestamp=" + timestamp +
                '}';
    }
}
