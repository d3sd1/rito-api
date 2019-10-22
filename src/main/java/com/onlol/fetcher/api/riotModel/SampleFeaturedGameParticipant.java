package com.onlol.fetcher.api.riotModel;

public class SampleFeaturedGameParticipant {
    private Long profileIconId;
    private Long championId;
    private String summonerName;
    private Boolean bot;
    private Long spell2Id;
    private Long teamId;
    private Long spell1Id;

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
