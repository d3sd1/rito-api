package com.onlol.fetcher.api.riotModel;

public class SampleBannedChampion {
    private Integer pickTurn;
    private Long championId;
    private Long teamId;

    public Integer getPickTurn() {
        return pickTurn;
    }

    public void setPickTurn(Integer pickTurn) {
        this.pickTurn = pickTurn;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getChampionId() {
        return championId;
    }

    public void setChampionId(Long championId) {
        this.championId = championId;
    }

    @Override
    public String toString() {
        return "SampleBannedChampion{" +
                "pickTurn=" + pickTurn +
                ", championId=" + championId +
                ", teamId=" + teamId +
                '}';
    }
}
