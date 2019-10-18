package com.onlol.fetcher.api.sampleModel;

import java.util.Arrays;

public class SampleSummonerLeague {
    private String queueType; // present only sometimes
    private String summonerName;
    private boolean hotStreak;
    private SampleSummonerLeagueSeries miniSeries;
    private Integer wins; //	int	Winning team on Summoners Rift. First placement in Teamfight Tactics.
    private boolean veteran;
    private Integer losses; //int	Losing team on Summoners Rift. Second through eighth placement in Teamfight Tactics.
    private String rank;
    private String leagueId;
    private boolean inactive;
    private boolean freshBlood;
    private String tier;
    private String summonerId;
    private Short leaguePoints;

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

    public SampleSummonerLeagueSeries getMiniSeries() {
        return miniSeries;
    }

    public void setMiniSeries(SampleSummonerLeagueSeries miniSeries) {
        this.miniSeries = miniSeries;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public boolean isVeteran() {
        return veteran;
    }

    public void setVeteran(boolean veteran) {
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

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public boolean isFreshBlood() {
        return freshBlood;
    }

    public void setFreshBlood(boolean freshBlood) {
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

    public Short getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(Short leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    @Override
    public String toString() {
        return "SampleSummonerLeague{" +
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
