package com.onlol.fetcher.api.riotModel;

public class SampleTeamBans {
    private Integer pickTurn;
    private Integer championId;

    public Integer getPickTurn() {
        return pickTurn;
    }

    public void setPickTurn(Integer pickTurn) {
        this.pickTurn = pickTurn;
    }

    public Integer getChampionId() {
        return championId;
    }

    public void setChampionId(Integer championId) {
        this.championId = championId;
    }

    @Override
    public String toString() {
        return "SampleTeamBans{" +
                "pickTurn=" + pickTurn +
                ", championId=" + championId +
                '}';
    }
}
