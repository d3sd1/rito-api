package com.onlol.fetcher.api.model;

/*
/lol/match/v4/timelines/by-match/{matchId}
 */
public class ApiMatchParticipantFrameDTO {
    private Integer totalGold = 0;
    private Integer teamScore = 0;
    private Integer participantId = 0;
    private Integer level = 0;
    private Integer currentGold = 0;
    private Integer minionsKilled = 0;
    private Integer dominionScore = 0;
    private ApiMatchPositionDTO position = new ApiMatchPositionDTO();
    private Integer xp = 0;
    private Integer jungleMinionsKilled = 0;

    public Integer getTotalGold() {
        return totalGold;
    }

    public void setTotalGold(Integer totalGold) {
        this.totalGold = totalGold;
    }

    public Integer getTeamScore() {
        return teamScore;
    }

    public void setTeamScore(Integer teamScore) {
        this.teamScore = teamScore;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCurrentGold() {
        return currentGold;
    }

    public void setCurrentGold(Integer currentGold) {
        this.currentGold = currentGold;
    }

    public Integer getMinionsKilled() {
        return minionsKilled;
    }

    public void setMinionsKilled(Integer minionsKilled) {
        this.minionsKilled = minionsKilled;
    }

    public Integer getDominionScore() {
        return dominionScore;
    }

    public void setDominionScore(Integer dominionScore) {
        this.dominionScore = dominionScore;
    }

    public ApiMatchPositionDTO getPosition() {
        return position;
    }

    public void setPosition(ApiMatchPositionDTO position) {
        this.position = position;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Integer getJungleMinionsKilled() {
        return jungleMinionsKilled;
    }

    public void setJungleMinionsKilled(Integer jungleMinionsKilled) {
        this.jungleMinionsKilled = jungleMinionsKilled;
    }

    @Override
    public String toString() {
        return "ApiMatchParticipantFrameDto{" +
                "totalGold=" + totalGold +
                ", teamScore=" + teamScore +
                ", participantId=" + participantId +
                ", level=" + level +
                ", currentGold=" + currentGold +
                ", minionsKilled=" + minionsKilled +
                ", dominionScore=" + dominionScore +
                ", position=" + position +
                ", xp=" + xp +
                ", jungleMinionsKilled=" + jungleMinionsKilled +
                '}';
    }
}
