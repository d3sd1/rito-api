package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchGame {
    @Id
    @Column(nullable = false, unique = true)
    private Long gameId;

    @Column(nullable = false, unique = false)
    private boolean retrieved = false;

    @Column(nullable = false, unique = false)
    private boolean retrieving = false;

    @Column(nullable = false, unique = false)
    private boolean expired = false; // if api returns 404

    @OneToOne
    private Season season;

    @OneToOne
    private Queue queue;

    @Column(nullable = true, unique = false)
    private String gameVersion; // major.minor version

    @OneToOne
    private Platform platform;

    @OneToOne
    private GameMap gameMap;

    @OneToOne
    private GameMode gameMode;

    @OneToOne
    private GameType gameType;

    @Column(nullable = false, unique = false)
    private Long gameDuration = 0L; // Match duration in seconds

    @Column(nullable = true, unique = false)
    private LocalDateTime gameCreation; // Designates the timestamp when champion select ended and the loading screen appeared, NOT when the game timer was at 0:00.
/*
TODO: meter estas dos key
    teams	List[TeamStatsDto]	Team information.
    participants	List[ParticipantDto]	Participant information.
*/

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public boolean isRetrieved() {
        return retrieved;
    }

    public void setRetrieved(boolean retrieved) {
        this.retrieved = retrieved;
    }

    public boolean isRetrieving() {
        return retrieving;
    }

    public void setRetrieving(boolean retrieving) {
        this.retrieving = retrieving;
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

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Long getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(Long gameDuration) {
        this.gameDuration = gameDuration;
    }

    public LocalDateTime getGameCreation() {
        return gameCreation;
    }

    public void setGameCreation(LocalDateTime gameCreation) {
        this.gameCreation = gameCreation;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "MatchGame{" +
                "gameId=" + gameId +
                ", retrieved=" + retrieved +
                ", retrieving=" + retrieving +
                ", expired=" + expired +
                ", season=" + season +
                ", queue=" + queue +
                ", gameVersion='" + gameVersion + '\'' +
                ", platform=" + platform +
                ", gameMap=" + gameMap +
                ", gameMode=" + gameMode +
                ", gameType=" + gameType +
                ", gameDuration=" + gameDuration +
                ", gameCreation=" + gameCreation +
                '}';
    }
}
