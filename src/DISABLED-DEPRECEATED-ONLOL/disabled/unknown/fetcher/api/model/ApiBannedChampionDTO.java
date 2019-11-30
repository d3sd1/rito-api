package status.disabled.unknown.fetcher.api.model;

/*
/lol/spectator/v4/featured-games
 */
public class ApiBannedChampionDTO {
    private Integer pickTurn = 0; // The turn during which the champion was banned
    private Long championId = 0L; // The ID of the banned champion
    private Long teamId = 0L; // The ID of the team that banned the champion

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
