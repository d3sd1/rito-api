package com.onlol.fetcher.api.model;

import java.util.ArrayList;
import java.util.List;

/*
/lol/spectator/v4/featured-games
 */
public class ApiFeaturedGameInfoDTO {
    private Long gameId = 0L; // The ID of the game
    private Long gameStartTime = 0L; // The game start time represented in epoch milliseconds
    private String platformId = ""; // The ID of the platform on which the game is being played
    private String gameMode = ""; // The game mode (Legal values: CLASSIC, ODIN, ARAM, TUTORIAL, ONEFORALL, ASCENSION, FIRSTBLOOD, KINGPORO)
    private Long mapId = 0L; // The ID of the map
    private String gameType = ""; // The game type (Legal values: CUSTOM_GAME, MATCHED_GAME, TUTORIAL_GAME)
    private List<ApiBannedChampionDTO> bannedChampions = new ArrayList<>(); // Banned champion information
    private ApiObserverDTO observers = new ApiObserverDTO(); // The observer information
    private List<ApiObserverParticipantDTO> participants = new ArrayList<>(); // The participant information
    private Long gameLength = 0L; // The amount of time in seconds that has passed since the game started
    private Long gameQueueConfigId = 0L; // The queue type (queue types are documented on the Game Constants page)

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(Long gameStartTime) {
        this.gameStartTime = gameStartTime;
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

    public Long getMapId() {
        return mapId;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public List<ApiBannedChampionDTO> getBannedChampions() {
        return bannedChampions;
    }

    public void setBannedChampions(List<ApiBannedChampionDTO> bannedChampions) {
        this.bannedChampions = bannedChampions;
    }

    public ApiObserverDTO getObservers() {
        return observers;
    }

    public void setObservers(ApiObserverDTO observers) {
        this.observers = observers;
    }

    public List<ApiObserverParticipantDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ApiObserverParticipantDTO> participants) {
        this.participants = participants;
    }

    public Long getGameLength() {
        return gameLength;
    }

    public void setGameLength(Long gameLength) {
        this.gameLength = gameLength;
    }

    public Long getGameQueueConfigId() {
        return gameQueueConfigId;
    }

    public void setGameQueueConfigId(Long gameQueueConfigId) {
        this.gameQueueConfigId = gameQueueConfigId;
    }

    @Override
    public String toString() {
        return "SampleFeaturedGameInfo{" +
                "gameId=" + gameId +
                ", gameStartTime=" + gameStartTime +
                ", platformId='" + platformId + '\'' +
                ", gameMode='" + gameMode + '\'' +
                ", mapId=" + mapId +
                ", gameType='" + gameType + '\'' +
                ", bannedChampions=" + bannedChampions +
                ", observers=" + observers +
                ", participants=" + participants +
                ", gameLength=" + gameLength +
                ", gameQueueConfigId=" + gameQueueConfigId +
                '}';
    }
}
