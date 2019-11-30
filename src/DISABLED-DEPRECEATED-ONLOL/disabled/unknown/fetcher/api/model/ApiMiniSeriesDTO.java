package status.disabled.unknown.fetcher.api.model;

/*
/lol/league/v4/challengerleagues/by-queue/{queue}
/lol/league/v4/entries/by-summoner/{encryptedSummonerId}
/lol/league/v4/entries/{queue}/{tier}/{division}
/lol/league/v4/grandmasterleagues/by-queue/{queue}
/lol/league/v4/masterleagues/by-queue/{queue}
 */
public class ApiMiniSeriesDTO {
    private String progress = "";
    private Integer losses = 0;
    private Short target = 0;
    private Integer wins = 0;

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

    public Short getTarget() {
        return target;
    }

    public void setTarget(Short target) {
        this.target = target;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    @Override
    public String toString() {
        return "ApiMiniSeriesDTO{" +
                "progress='" + progress + '\'' +
                ", losses=" + losses +
                ", target=" + target +
                ", wins=" + wins +
                '}';
    }
}
