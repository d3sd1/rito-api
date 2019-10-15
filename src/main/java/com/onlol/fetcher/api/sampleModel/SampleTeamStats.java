package com.onlol.fetcher.api.sampleModel;

import java.util.List;

public class SampleTeamStats {
    private boolean firstDragon;
    private boolean firstInhibitor;
    private List<SampleTeamBans> bans;
    private Integer baronKills;
    private boolean firstRiftHerald;
    private boolean firstBaron;
    private Integer riftHeraldKills;
    private boolean firstBlood;
    private Integer teamId; // int	100 for blue side. 200 for red side.
    private boolean firstTower;
    private Integer vilemawKills;
    private Integer inhibitorKills;
    private Integer towerKills;
    private Integer dominionVictoryScore;
    private String win; // String indicating whether or not the team won. There are only two values visibile in public match history. (Legal values: Fail, Win)
    private Integer dragonKills;

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

    public List<SampleTeamBans> getBans() {
        return bans;
    }

    public void setBans(List<SampleTeamBans> bans) {
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
