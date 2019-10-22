package com.onlol.fetcher.api.riotModel;

import java.util.List;

public class SampleFeaturedGameInfo {
    private Long gameId;
    private Long gameStartTime;
    private String platformId;
    private String gameMode; // (Legal values: CLASSIC, ODIN, ARAM, TUTORIAL, ONEFORALL, ASCENSION, FIRSTBLOOD, KINGPORO)
    private Long mapId;
    private String gameType; //  (Legal values: CUSTOM_GAME, MATCHED_GAME, TUTORIAL_GAME)
    private List<SampleBannedChampion> bannedChampions;
    private SampleObserver observers;
    private List<SampleFeaturedGameParticipant> participants;
    private Long gameLength;
    private Long gameQueueConfigId;

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

    public List<SampleBannedChampion> getBannedChampions() {
        return bannedChampions;
    }

    public void setBannedChampions(List<SampleBannedChampion> bannedChampions) {
        this.bannedChampions = bannedChampions;
    }

    public SampleObserver getObservers() {
        return observers;
    }

    public void setObservers(SampleObserver observers) {
        this.observers = observers;
    }

    public List<SampleFeaturedGameParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<SampleFeaturedGameParticipant> participants) {
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
