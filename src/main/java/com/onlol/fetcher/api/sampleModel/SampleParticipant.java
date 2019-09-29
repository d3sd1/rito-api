package com.onlol.fetcher.api.sampleModel;

import java.util.List;

public class SampleParticipant {
    private SampleParticipantStats stats;
    private Integer participantId;
    private List<SampleRune> runes; // List of legacy Rune information. Not included for matches played with Runes Reforged.
    private SampleParticipantTimeline timeline;
    private Integer teamId; // int	100 for blue side. 200 for red side.
    private Integer championId;
    private Integer spell1Id;
    private Integer spell2Id;
    private String highestAchievedSeasonTier;
    private List<SampleMastery> masteries;

    public SampleParticipantStats getStats() {
        return stats;
    }

    public void setStats(SampleParticipantStats stats) {
        this.stats = stats;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public List<SampleRune> getRunes() {
        return runes;
    }

    public void setRunes(List<SampleRune> runes) {
        this.runes = runes;
    }

    public SampleParticipantTimeline getTimeline() {
        return timeline;
    }

    public void setTimeline(SampleParticipantTimeline timeline) {
        this.timeline = timeline;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getChampionId() {
        return championId;
    }

    public void setChampionId(Integer championId) {
        this.championId = championId;
    }

    public Integer getSpell1Id() {
        return spell1Id;
    }

    public void setSpell1Id(Integer spell1Id) {
        this.spell1Id = spell1Id;
    }

    public Integer getSpell2Id() {
        return spell2Id;
    }

    public void setSpell2Id(Integer spell2Id) {
        this.spell2Id = spell2Id;
    }

    public String getHighestAchievedSeasonTier() {
        return highestAchievedSeasonTier;
    }

    public void setHighestAchievedSeasonTier(String highestAchievedSeasonTier) {
        this.highestAchievedSeasonTier = highestAchievedSeasonTier;
    }

    public List<SampleMastery> getMasteries() {
        return masteries;
    }

    public void setMasteries(List<SampleMastery> masteries) {
        this.masteries = masteries;
    }

    @Override
    public String toString() {
        return "SampleParticipant{" +
                "stats=" + stats +
                ", participantId=" + participantId +
                ", runes=" + runes +
                ", timeline=" + timeline +
                ", teamId=" + teamId +
                ", championId=" + championId +
                ", spell1Id=" + spell1Id +
                ", spell2Id=" + spell2Id +
                ", highestAchievedSeasonTier='" + highestAchievedSeasonTier + '\'' +
                ", masteries=" + masteries +
                '}';
    }
}
