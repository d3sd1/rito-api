package com.onlol.fetcher.api.model;

/*
/lol/match/v4/matches/{matchId}
 */
public class ApiMasteryDTO {
    private Integer masteryId = 0;
    private Integer rank = 0;

    public Integer getMasteryId() {
        return masteryId;
    }

    public void setMasteryId(Integer masteryId) {
        this.masteryId = masteryId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "SampleMastery{" +
                "masteryId=" + masteryId +
                ", rank=" + rank +
                '}';
    }
}
