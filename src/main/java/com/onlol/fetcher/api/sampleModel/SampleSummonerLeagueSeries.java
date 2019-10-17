package com.onlol.fetcher.api.sampleModel;

public class SampleSummonerLeagueSeries {
    private String progress;
    private Integer losses;
    private Integer target;
    private Integer wins;

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
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
