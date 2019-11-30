package status.disabled.fetcher.api.model;

/*
/lol/league/v4/entries/by-summoner/{encryptedSummonerId}
/lol/league/v4/entries/{queue}/{tier}/{division}
 */
public class ApiLeagueEntryDTO {
    private String queueType = "";
    private String summonerName = "";
    private boolean hotStreak = false;
    private ApiMiniSeriesDTO miniSeries = new ApiMiniSeriesDTO();
    private Integer wins = 0; // int	Winning team on Summoners Rift. First placement in Teamfight Tactics.
    private Boolean veteran = false;
    private Integer losses = 0; //int	Losing team on Summoners Rift. Second through eighth placement in Teamfight Tactics.
    private String rank = "";
    private String leagueId = "";
    private Boolean inactive = false;
    private Boolean freshBlood = false;
    private String tier = "";
    private String summonerId = ""; // string	Player's summonerId (Encrypted)
    private Integer leaguePoints = 0;

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public boolean isHotStreak() {
        return hotStreak;
    }

    public void setHotStreak(boolean hotStreak) {
        this.hotStreak = hotStreak;
    }

    public ApiMiniSeriesDTO getMiniSeries() {
        return miniSeries;
    }

    public void setMiniSeries(ApiMiniSeriesDTO miniSeries) {
        this.miniSeries = miniSeries;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Boolean getVeteran() {
        return veteran;
    }

    public void setVeteran(Boolean veteran) {
        this.veteran = veteran;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    public Boolean getFreshBlood() {
        return freshBlood;
    }

    public void setFreshBlood(Boolean freshBlood) {
        this.freshBlood = freshBlood;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public Integer getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(Integer leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    @Override
    public String toString() {
        return "ApiLeagueEntryDTO{" +
                "queueType='" + queueType + '\'' +
                ", summonerName='" + summonerName + '\'' +
                ", hotStreak=" + hotStreak +
                ", miniSeries=" + miniSeries +
                ", wins=" + wins +
                ", veteran=" + veteran +
                ", losses=" + losses +
                ", rank='" + rank + '\'' +
                ", leagueId='" + leagueId + '\'' +
                ", inactive=" + inactive +
                ", freshBlood=" + freshBlood +
                ", tier='" + tier + '\'' +
                ", summonerId='" + summonerId + '\'' +
                ", leaguePoints=" + leaguePoints +
                '}';
    }
}
