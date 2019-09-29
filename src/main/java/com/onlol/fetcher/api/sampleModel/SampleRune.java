package com.onlol.fetcher.api.sampleModel;

public class SampleRune {
    private Integer runeId;
    private Integer rank;

    public Integer getRuneId() {
        return runeId;
    }

    public void setRuneId(Integer runeId) {
        this.runeId = runeId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "SampleRune{" +
                "runeId=" + runeId +
                ", rank=" + rank +
                '}';
    }
}
