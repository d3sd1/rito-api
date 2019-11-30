package status.disabled.fetcher.api.model;

/*
CONFLICTS WITH ApiObserverParticipant (renamed)
/lol/match/v4/matches/{matchId}
 */
public class ApiObserverParticipantDTO {
    private Long profileIconId = 0L; // The ID of the profile icon used by this participant
    private Long championId = 0L; // The ID of the champion played by this participant
    private String summonerName = ""; // The summoner name of this participant
    private Boolean bot = false; // Flag indicating whether or not this participant is a bot
    private Long spell2Id = 0L; // The ID of the second summoner spell used by this participant
    private Long teamId = 0L; // The team ID of this participant, indicating the participant's team
    private Long spell1Id = 0L; // The ID of the first summoner spell used by this participant

    public Long getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(Long profileIconId) {
        this.profileIconId = profileIconId;
    }

    public Long getChampionId() {
        return championId;
    }

    public void setChampionId(Long championId) {
        this.championId = championId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public Boolean getBot() {
        return bot;
    }

    public void setBot(Boolean bot) {
        this.bot = bot;
    }

    public Long getSpell2Id() {
        return spell2Id;
    }

    public void setSpell2Id(Long spell2Id) {
        this.spell2Id = spell2Id;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getSpell1Id() {
        return spell1Id;
    }

    public void setSpell1Id(Long spell1Id) {
        this.spell1Id = spell1Id;
    }

    @Override
    public String toString() {
        return "SampleFeaturedGameParticipant{" +
                "profileIconId=" + profileIconId +
                ", championId=" + championId +
                ", summonerName='" + summonerName + '\'' +
                ", bot=" + bot +
                ", spell2Id=" + spell2Id +
                ", teamId=" + teamId +
                ", spell1Id=" + spell1Id +
                '}';
    }
}
