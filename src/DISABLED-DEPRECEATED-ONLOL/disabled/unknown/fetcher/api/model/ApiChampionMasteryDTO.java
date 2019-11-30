package status.disabled.unknown.fetcher.api.model;

/*
Used on endpoints:
/lol/champion-mastery/v4/champion-masteries/by-summoner/{encryptedSummonerId}
/lol/champion-mastery/v4/champion-masteries/by-summoner/{encryptedSummonerId}/by-champion/{championId}
 */
public class ApiChampionMasteryDTO {
    private Long id = 0L;
    private Integer championLevel = 0; // int	Champion level for specified player and champion combination.
    private boolean chestGranted = false; // boolean	Is chest granted for this champion or not in current season.
    private Integer championPoints = 0; // int	Total number of champion points for this player and champion combination - they are used to determine championLevel.
    private Long championPointsSinceLastLevel = 0L; // long	Number of points earned since current level has been achieved.
    private Long championPointsUntilNextLevel = 0L; // long	Number of points needed to achieve next level. Zero if player reached maximum champion level for this champion.
    private Integer tokensEarned = 0; // int	The token earned for this champion to levelup.
    private String summonerId = ""; // string	Summoner ID for this entry. (Encrypted)
    private Long championId = 0L; // long	Champion ID for this entry.
    private Long lastPlayTime = 0L; // long	Last time this champion was played by this player - in Unix milliseconds time format.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getChampionLevel() {
        return championLevel;
    }

    public void setChampionLevel(Integer championLevel) {
        this.championLevel = championLevel;
    }

    public boolean isChestGranted() {
        return chestGranted;
    }

    public void setChestGranted(boolean chestGranted) {
        this.chestGranted = chestGranted;
    }

    public Integer getChampionPoints() {
        return championPoints;
    }

    public void setChampionPoints(Integer championPoints) {
        this.championPoints = championPoints;
    }

    public Long getChampionPointsSinceLastLevel() {
        return championPointsSinceLastLevel;
    }

    public void setChampionPointsSinceLastLevel(Long championPointsSinceLastLevel) {
        this.championPointsSinceLastLevel = championPointsSinceLastLevel;
    }

    public Long getChampionPointsUntilNextLevel() {
        return championPointsUntilNextLevel;
    }

    public void setChampionPointsUntilNextLevel(Long championPointsUntilNextLevel) {
        this.championPointsUntilNextLevel = championPointsUntilNextLevel;
    }

    public Integer getTokensEarned() {
        return tokensEarned;
    }

    public void setTokensEarned(Integer tokensEarned) {
        this.tokensEarned = tokensEarned;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public Long getChampionId() {
        return championId;
    }

    public void setChampionId(Long championId) {
        this.championId = championId;
    }

    public Long getLastPlayTime() {
        return lastPlayTime;
    }

    public void setLastPlayTime(Long lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    @Override
    public String toString() {
        return "ChampionMasteryDTO{" +
                "id=" + id +
                ", championLevel=" + championLevel +
                ", chestGranted=" + chestGranted +
                ", championPoints=" + championPoints +
                ", championPointsSinceLastLevel=" + championPointsSinceLastLevel +
                ", championPointsUntilNextLevel=" + championPointsUntilNextLevel +
                ", tokensEarned=" + tokensEarned +
                ", summonerId='" + summonerId + '\'' +
                ", championId=" + championId +
                ", lastPlayTime=" + lastPlayTime +
                '}';
    }
}
