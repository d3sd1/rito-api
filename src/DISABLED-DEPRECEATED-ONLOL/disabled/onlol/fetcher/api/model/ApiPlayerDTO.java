package status.disabled.onlol.fetcher.api.model;

/*
/lol/match/v4/matches/{matchId}
 */
public class ApiPlayerDTO {
    private String currentPlatformId = "";
    private String summonerName = "";
    private String matchHistoryUri = "";
    private String platformId = ""; // Original platformId.
    private String currentAccountId = ""; // Player's current accountId (Encrypted)
    private Integer profileIcon = 0;
    private String summonerId = ""; // Player's summonerId (Encrypted)
    private String accountId = ""; // Player's original accountId (Encrypted)

    public String getCurrentPlatformId() {
        return currentPlatformId;
    }

    public void setCurrentPlatformId(String currentPlatformId) {
        this.currentPlatformId = currentPlatformId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getMatchHistoryUri() {
        return matchHistoryUri;
    }

    public void setMatchHistoryUri(String matchHistoryUri) {
        this.matchHistoryUri = matchHistoryUri;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(String currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public Integer getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(Integer profileIcon) {
        this.profileIcon = profileIcon;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "SamplePlayer{" +
                "currentPlatformId='" + currentPlatformId + '\'' +
                ", summonerName='" + summonerName + '\'' +
                ", matchHistoryUri='" + matchHistoryUri + '\'' +
                ", platformId='" + platformId + '\'' +
                ", currentAccountId='" + currentAccountId + '\'' +
                ", profileIcon=" + profileIcon +
                ", summonerId='" + summonerId + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
