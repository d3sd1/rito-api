package status.disabled.onlol.fetcher.api.model;


/*
/lol/match/v4/matchlists/by-account/{encryptedAccountId}
 */
public class ApiMatchReferenceDTO {
    private String lane = "";
    private Long gameId = 0L;
    private Long champion = 0L;
    private String platformId = "";
    private Integer season;
    private Long timestamp = 0L;
    private Integer queue = 0;
    private String role = "";

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getChampion() {
        return champion;
    }

    public void setChampion(Long champion) {
        this.champion = champion;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getQueue() {
        return queue;
    }

    public void setQueue(Integer queue) {
        this.queue = queue;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    @Override
    public String toString() {
        return "MatchList{" +
                "lane='" + lane + '\'' +
                ", gameId=" + gameId +
                ", champion=" + champion +
                ", platformId='" + platformId + '\'' +
                ", timestamp=" + timestamp +
                ", queue=" + queue +
                ", role='" + role + '\'' +
                ", season=" + season +
                '}';
    }
}
