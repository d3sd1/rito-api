package com.onlol.fetcher.api.riotModel;

import java.util.List;

public class SampleMatchGame {
    private Integer seasonId;
    private Integer queueId;
    private Long gameId;
    private List<SampleParticipantIdentity> participantIdentities;
    private String gameVersion;
    private String platformId;
    private String gameMode;
    private Integer mapId;
    private String gameType;
    private List<SampleTeamStats> teams;
    private List<SampleParticipant> participants;
    private Long gameDuration;
    private Long gameCreation;

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public Integer getQueueId() {
        return queueId;
    }

    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public List<SampleParticipantIdentity> getParticipantIdentities() {
        return participantIdentities;
    }

    public void setParticipantIdentities(List<SampleParticipantIdentity> participantIdentities) {
        this.participantIdentities = participantIdentities;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public List<SampleTeamStats> getTeams() {
        return teams;
    }

    public void setTeams(List<SampleTeamStats> teams) {
        this.teams = teams;
    }

    public List<SampleParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<SampleParticipant> participants) {
        this.participants = participants;
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

    @Override
    public String toString() {
        return "SampleMatchGame{" +
                "seasonId=" + seasonId +
                ", queueId=" + queueId +
                ", gameId=" + gameId +
                ", participantIdentities=" + participantIdentities +
                ", gameVersion='" + gameVersion + '\'' +
                ", platformId='" + platformId + '\'' +
                ", gameMode='" + gameMode + '\'' +
                ", mapId=" + mapId +
                ", gameType='" + gameType + '\'' +
                ", teams=" + teams +
                ", participants=" + participants +
                ", gameDuration=" + gameDuration +
                ", gameCreation=" + gameCreation +
                '}';
    }
}
