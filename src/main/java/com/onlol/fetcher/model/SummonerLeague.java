package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummonerLeague {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne
    private Summoner summoner;

    @OneToOne
    private QueueType queueType;

    @OneToOne
    private LeagueTier leagueTier;

    @OneToOne
    private LeagueRank leagueRank;

    @OneToOne(orphanRemoval = true)
    private LeagueMiniSeries leagueMiniSeries;

    @Column(nullable = false, unique = false)
    private boolean hotStreak = false;

    @Column(nullable = false, unique = false)
    private Integer wins = 0;

    @Column(nullable = false, unique = false)
    private Integer losses = 0;

    @Column(nullable = false, unique = false)
    private boolean veteran = false;

    @Column(nullable = false, unique = false)
    private boolean inactive = false;

    @Column(nullable = false, unique = false)
    private boolean freshBlood = false;

    @Column(nullable = false, unique = false)
    private Short leaguePoints = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeagueTier getLeagueTier() {
        return leagueTier;
    }

    public void setLeagueTier(LeagueTier leagueTier) {
        this.leagueTier = leagueTier;
    }

    public LeagueRank getLeagueRank() {
        return leagueRank;
    }

    public void setLeagueRank(LeagueRank leagueRank) {
        this.leagueRank = leagueRank;
    }

    public LeagueMiniSeries getLeagueMiniSeries() {
        return leagueMiniSeries;
    }

    public void setLeagueMiniSeries(LeagueMiniSeries leagueMiniSeries) {
        this.leagueMiniSeries = leagueMiniSeries;
    }

    public Summoner getSummoner() {
        return summoner;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

    public boolean isHotStreak() {
        return hotStreak;
    }

    public void setHotStreak(boolean hotStreak) {
        this.hotStreak = hotStreak;
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

    public boolean isVeteran() {
        return veteran;
    }

    public void setVeteran(boolean veteran) {
        this.veteran = veteran;
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

    public Short getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(Short leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    public QueueType getQueueType() {
        return queueType;
    }

    public void setQueueType(QueueType queueType) {
        this.queueType = queueType;
    }

    @Override
    public String toString() {
        return "SummonerLeague{" +
                "id=" + id +
                ", summoner=" + summoner +
                ", queueType=" + queueType +
                ", leagueTier=" + leagueTier +
                ", leagueRank=" + leagueRank +
                ", leagueMiniSeries=" + leagueMiniSeries +
                ", hotStreak=" + hotStreak +
                ", wins=" + wins +
                ", losses=" + losses +
                ", veteran=" + veteran +
                ", inactive=" + inactive +
                ", freshBlood=" + freshBlood +
                ", leaguePoints=" + leaguePoints +
                '}';
    }
}
