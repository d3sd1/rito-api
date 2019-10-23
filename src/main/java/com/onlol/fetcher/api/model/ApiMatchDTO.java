package com.onlol.fetcher.api.model;

import java.util.ArrayList;
import java.util.List;

/*
/lol/match/v4/matches/{matchId}
 */
public class ApiMatchDTO {
    private Integer seasonId = 0; // int	Please refer to the Game Constants documentation.
    private Integer queueId = 0; // int	Please refer to the Game Constants documentation.
    private Long gameId = 0L;
    private List<ApiParticipantIdentityDTO> participantIdentities = new ArrayList<>(); // Participant identity information.
    private String gameVersion = ""; // The major.minor version typically indicates the patch the match was played on.
    private String platformId = ""; // Platform where the match was played.
    private String gameMode = ""; // Please refer to the Game Constants documentation.
    private Integer mapId = 0; // Please refer to the Game Constants documentation.
    private String gameType = ""; // Please refer to the Game Constants documentation.
    private List<ApiTeamStatsDTO> teams = new ArrayList<>(); // Team information.
    private List<ApiMatchParticipantDTO> participants = new ArrayList<>(); // Participant information.
    private Long gameDuration = 0L; // Match duration in seconds.
    private Long gameCreation = 0L; // Designates the timestamp when champion select ended and the loading screen appeared, NOT when the game timer was at 0:00.

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

    public List<ApiParticipantIdentityDTO> getParticipantIdentities() {
        return participantIdentities;
    }

    public void setParticipantIdentities(List<ApiParticipantIdentityDTO> participantIdentities) {
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

    public List<ApiTeamStatsDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<ApiTeamStatsDTO> teams) {
        this.teams = teams;
    }

    public List<ApiMatchParticipantDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ApiMatchParticipantDTO> participants) {
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
