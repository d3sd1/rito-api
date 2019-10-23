package com.onlol.fetcher.api.model;

import java.util.ArrayList;
import java.util.List;

/*
/lol/match/v4/matches/{matchId}
 */
public class ApiTeamStatsDTO {
    private boolean firstDragon = false; // Flag indicating whether or not the team scored the first Dragon kill.
    private boolean firstInhibitor = false; // Flag indicating whether or not the team destroyed the first inhibitor.
    private List<ApiTeamBansDTO> bans = new ArrayList<>(); // If match queueId has a draft, contains banned champion data, otherwise empty.
    private Integer baronKills = 0; // Number of times the team killed Baron.
    private boolean firstRiftHerald = false; // Flag indicating whether or not the team scored the first Rift Herald kill.
    private boolean firstBaron = false; // Flag indicating whether or not the team scored the first Baron kill.
    private Integer riftHeraldKills = 0; // Number of times the team killed Rift Herald.
    private boolean firstBlood = false; // Flag indicating whether or not the team scored the first blood.
    private Integer teamId = 0; // int	100 for blue side. 200 for red side.
    private boolean firstTower = false; // Flag indicating whether or not the team destroyed the first tower.
    private Integer vilemawKills = 0; // Number of times the team killed Vilemaw.
    private Integer inhibitorKills = 0; // Number of inhibitors the team destroyed.
    private Integer towerKills = 0; // Number of towers the team destroyed.
    private Integer dominionVictoryScore = 0; // For Dominion matches, specifies the points the team had at game end.
    private String win = ""; // String indicating whether or not the team won. There are only two values visibile in public match history. (Legal values: Fail, Win)
    private Integer dragonKills = 0; // Number of times the team killed Dragon.

    public boolean isFirstDragon() {
        return firstDragon;
    }

    public void setFirstDragon(boolean firstDragon) {
        this.firstDragon = firstDragon;
    }

    public boolean isFirstInhibitor() {
        return firstInhibitor;
    }

    public void setFirstInhibitor(boolean firstInhibitor) {
        this.firstInhibitor = firstInhibitor;
    }

    public List<ApiTeamBansDTO> getBans() {
        return bans;
    }

    public void setBans(List<ApiTeamBansDTO> bans) {
        this.bans = bans;
    }

    public Integer getBaronKills() {
        return baronKills;
    }

    public void setBaronKills(Integer baronKills) {
        this.baronKills = baronKills;
    }

    public boolean isFirstRiftHerald() {
        return firstRiftHerald;
    }

    public void setFirstRiftHerald(boolean firstRiftHerald) {
        this.firstRiftHerald = firstRiftHerald;
    }

    public boolean isFirstBaron() {
        return firstBaron;
    }

    public void setFirstBaron(boolean firstBaron) {
        this.firstBaron = firstBaron;
    }

    public Integer getRiftHeraldKills() {
        return riftHeraldKills;
    }

    public void setRiftHeraldKills(Integer riftHeraldKills) {
        this.riftHeraldKills = riftHeraldKills;
    }

    public boolean isFirstBlood() {
        return firstBlood;
    }

    public void setFirstBlood(boolean firstBlood) {
        this.firstBlood = firstBlood;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public boolean isFirstTower() {
        return firstTower;
    }

    public void setFirstTower(boolean firstTower) {
        this.firstTower = firstTower;
    }

    public Integer getVilemawKills() {
        return vilemawKills;
    }

    public void setVilemawKills(Integer vilemawKills) {
        this.vilemawKills = vilemawKills;
    }

    public Integer getInhibitorKills() {
        return inhibitorKills;
    }

    public void setInhibitorKills(Integer inhibitorKills) {
        this.inhibitorKills = inhibitorKills;
    }

    public Integer getTowerKills() {
        return towerKills;
    }

    public void setTowerKills(Integer towerKills) {
        this.towerKills = towerKills;
    }

    public Integer getDominionVictoryScore() {
        return dominionVictoryScore;
    }

    public void setDominionVictoryScore(Integer dominionVictoryScore) {
        this.dominionVictoryScore = dominionVictoryScore;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public Integer getDragonKills() {
        return dragonKills;
    }

    public void setDragonKills(Integer dragonKills) {
        this.dragonKills = dragonKills;
    }

    @Override
    public String toString() {
        return "SampleTeamStats{" +
                "firstDragon=" + firstDragon +
                ", firstInhibitor=" + firstInhibitor +
                ", bans=" + bans +
                ", baronKills=" + baronKills +
                ", firstRiftHerald=" + firstRiftHerald +
                ", firstBaron=" + firstBaron +
                ", riftHeraldKills=" + riftHeraldKills +
                ", firstBlood=" + firstBlood +
                ", teamId=" + teamId +
                ", firstTower=" + firstTower +
                ", vilemawKills=" + vilemawKills +
                ", inhibitorKills=" + inhibitorKills +
                ", towerKills=" + towerKills +
                ", dominionVictoryScore=" + dominionVictoryScore +
                ", win='" + win + '\'' +
                ", dragonKills=" + dragonKills +
                '}';
    }
}
