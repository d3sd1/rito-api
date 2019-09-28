package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
    private Season season;

    @Column(nullable = false, unique = false)
    private Queue queue;

    @Column(nullable = false, unique = false)
    private String gameVersion; // major.minor version

    @Column(nullable = false, unique = false)
    private Platform platform;

    @Column(nullable = false, unique = false)
    private Long gameDuration; // Match duration in seconds

    @Column(nullable = false, unique = false)
    private Long gameCreation; // Designates the timestamp when champion select ended and the loading screen appeared, NOT when the game timer was at 0:00.
/*
    participantIdentities	List[ParticipantIdentityDto]	Participant identity information.
    gameMode	string	Please refer to the Game Constants documentation.
    mapId	int	Please refer to the Game Constants documentation.
    gameType	string	Please refer to the Game Constants documentation.
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

    public Long getGameCreation() {
        return gameCreation;
    }

    public void setGameCreation(Long gameCreation) {
        this.gameCreation = gameCreation;
    }
}
