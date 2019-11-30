package com.onlol.database.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchGameParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne
    private MatchGame matchGame;

    @OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private Summoner summoner; // FROM participantId

    @OneToOne
    private Champion champion;

    @OneToOne
    private SummonerSpell spell1;

    @OneToOne
    private SummonerSpell spell2;

    @OneToMany
    private List<SummonerLegacyRune> legacyRunes;

    @OneToMany
    private List<SummonerLegacyMastery> legacyMasteries;

    @OneToOne
    private MatchGameTeam team;

    @OneToOne
    private MatchGameParticipantStats stats; //	ParticipantStatsDto	Participant statistics.

    @OneToOne
    private MatchGameTimeline timeline;

    @OneToOne
    private LeagueTier highestAchievedSeasonTier; //Highest ranked tier achieved for the previous season in a specific subset of queueIds, if any, otherwise null. Used to display border in game loading screen. Please refer to the Ranked Info documentation. (Legal values: CHALLENGER, MASTER, DIAMOND, PLATINUM, GOLD, SILVER, BRONZE, UNRANKED)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MatchGame getMatchGame() {
        return matchGame;
    }

    public void setMatchGame(MatchGame matchGame) {
        this.matchGame = matchGame;
    }

    public Summoner getSummoner() {
        return summoner;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }

    public SummonerSpell getSpell1() {
        return spell1;
    }

    public void setSpell1(SummonerSpell spell1) {
        this.spell1 = spell1;
    }

    public SummonerSpell getSpell2() {
        return spell2;
    }

    public void setSpell2(SummonerSpell spell2) {
        this.spell2 = spell2;
    }

    public List<SummonerLegacyRune> getLegacyRunes() {
        return legacyRunes;
    }

    public void setLegacyRunes(List<SummonerLegacyRune> legacyRunes) {
        this.legacyRunes = legacyRunes;
    }

    public List<SummonerLegacyMastery> getLegacyMasteries() {
        return legacyMasteries;
    }

    public void setLegacyMasteries(List<SummonerLegacyMastery> legacyMasteries) {
        this.legacyMasteries = legacyMasteries;
    }

    public MatchGameTeam getTeam() {
        return team;
    }

    public void setTeam(MatchGameTeam team) {
        this.team = team;
    }

    public MatchGameParticipantStats getStats() {
        return stats;
    }

    public void setStats(MatchGameParticipantStats stats) {
        this.stats = stats;
    }

    public MatchGameTimeline getTimeline() {
        return timeline;
    }

    public void setTimeline(MatchGameTimeline timeline) {
        this.timeline = timeline;
    }

    public LeagueTier getHighestAchievedSeasonTier() {
        return highestAchievedSeasonTier;
    }

    public void setHighestAchievedSeasonTier(LeagueTier highestAchievedSeasonTier) {
        this.highestAchievedSeasonTier = highestAchievedSeasonTier;
    }

    @Override
    public String toString() {
        return "MatchGameParticipant{" +
                "id=" + id +
                ", matchGame=" + matchGame +
                ", summoner=" + summoner +
                ", champion=" + champion +
                ", spell1=" + spell1 +
                ", spell2=" + spell2 +
                ", legacyRunes=" + legacyRunes +
                ", legacyMasteries=" + legacyMasteries +
                ", team=" + team +
                ", stats=" + stats +
                ", timeline=" + timeline +
                ", highestAchievedSeasonTier=" + highestAchievedSeasonTier +
                '}';
    }
}
