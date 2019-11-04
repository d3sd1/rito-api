package com.onlol.fetcher.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummonerChampionMastery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false)
    private Integer championLevel = 0;

    @Column(nullable = false, unique = false)
    private boolean chestGranted = false;

    @Column(nullable = false, unique = false)
    private Integer championPoints = 0;

    @Column(nullable = false, unique = false)
    private Long championPointsSinceLastLevel = 0L;

    @Column(nullable = false, unique = false)
    private Long championPointsUntilNextLevel = 0L;

    @Column(nullable = false, unique = false)
    private Integer tokensEarned = 0;

    @OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @JsonProperty(value="summonerId")
    private Summoner summoner;

    @OneToOne
    @JsonProperty(value="championId")
    private Champion champion;

    @Column(nullable = true, unique = false)
    private LocalDateTime lastPlayTime;

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

    public LocalDateTime getLastPlayTime() {
        return lastPlayTime;
    }

    public void setLastPlayTime(LocalDateTime lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    @Override
    public String toString() {
        return "SummonerChampionMastery{" +
                "id='" + id + '\'' +
                ", championLevel=" + championLevel +
                ", chestGranted=" + chestGranted +
                ", championPoints=" + championPoints +
                ", championPointsSinceLastLevel=" + championPointsSinceLastLevel +
                ", championPointsUntilNextLevel=" + championPointsUntilNextLevel +
                ", tokensEarned=" + tokensEarned +
                ", summoner=" + summoner +
                ", champion=" + champion +
                ", lastPlayTime=" + lastPlayTime +
                '}';
    }
}
