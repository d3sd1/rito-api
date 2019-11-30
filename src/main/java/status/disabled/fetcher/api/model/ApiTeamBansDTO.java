package status.disabled.fetcher.api.model;

/*
/lol/match/v4/matches/{matchId}
 */
public class ApiTeamBansDTO {
    private Integer pickTurn = 0; // Turn during which the champion was banned.
    private Long championId = 0L; // Banned championId.

    public Integer getPickTurn() {
        return pickTurn;
    }

    public void setPickTurn(Integer pickTurn) {
        this.pickTurn = pickTurn;
    }

    public Long getChampionId() {
        return championId;
    }

    public void setChampionId(Long championId) {
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
