package status.disabled.unknown.fetcher.api.model;

/*
/lol/league/v4/challengerleagues/by-queue/{queue}
/lol/league/v4/grandmasterleagues/by-queue/{queue}
/lol/league/v4/masterleagues/by-queue/{queue}
 */
public class ApiLeagueItemDTO {
    private String summonerName = "";
    private boolean hotStreak = false;
    private ApiMiniSeriesDTO miniSeries = new ApiMiniSeriesDTO();
    private Integer wins = 0; //	int	Winning team on Summoners Rift. First placement in Teamfight Tactics.
    private boolean veteran = false;
    private Integer losses = 0; //int	Losing team on Summoners Rift. Second through eighth placement in Teamfight Tactics.
    private Boolean freshBlood = false;
    private boolean inactive = false;
    private String rank = "";
    private String summonerId = ""; // string	Player's summonerId (Encrypted)
    private Integer leaguePoints = 0;

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

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
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

    public boolean isVeteran() {
        return veteran;
    }

    public void setVeteran(boolean veteran) {
        this.veteran = veteran;
    }

    public Boolean getFreshBlood() {
        return freshBlood;
    }

    public void setFreshBlood(Boolean freshBlood) {
        this.freshBlood = freshBlood;
    }

    @Override
    public String toString() {
        return "ApiLeagueItemDTO{" +
                "summonerName='" + summonerName + '\'' +
                ", hotStreak=" + hotStreak +
                ", miniSeries=" + miniSeries +
                ", wins=" + wins +
                ", veteran=" + veteran +
                ", losses=" + losses +
                ", freshBlood=" + freshBlood +
                ", inactive=" + inactive +
                ", rank='" + rank + '\'' +
                ", summonerId='" + summonerId + '\'' +
                ", leaguePoints=" + leaguePoints +
                '}';
    }
}
