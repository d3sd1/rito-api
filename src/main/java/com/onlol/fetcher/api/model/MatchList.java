package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;
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
    private Platform platform; // platformId string

    @OneToOne
    private Season season; // season int

    @OneToOne
    private Queue queue; // season int

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

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
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
}
