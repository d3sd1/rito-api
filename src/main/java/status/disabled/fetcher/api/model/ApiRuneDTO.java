package status.disabled.fetcher.api.model;

/*
/lol/match/v4/matches/{matchId}
 */
public class ApiRuneDTO {
    private Integer runeId = 0;
    private Integer rank = 0;

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
