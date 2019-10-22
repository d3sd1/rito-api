package com.onlol.fetcher.api.riotModel;

public class SampleSummonerLeagueSeries {
    private String progress;
    private Integer losses;
    private Short target;
    private Integer wins;

    public Short getTarget() {
        return target;
    }

    public void setTarget(Short target) {
        this.target = target;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    @Override
    public String toString() {
        return "SampleSummonerLeagueSeries{" +
                "progress='" + progress + '\'' +
                ", losses=" + losses +
                ", target=" + target +
                ", wins=" + wins +
                '}';
    }
}
