package status.disabled.unknown.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchGameTeamStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false)
    private boolean firstDragon = false; // Flag indicating whether or not the team scored the first Dragon kill.

    @Column(nullable = false, unique = false)
    private boolean firstInhibitor = false; // Flag indicating whether or not the team destroyed the first inhibitor.

    @Column(nullable = false, unique = false)
    private boolean firstRiftHerald = false; // Flag indicating whether or not the team scored the first Rift Herald kill.

    @Column(nullable = false, unique = false)
    private boolean firstBaron = false; // Flag indicating whether or not the team scored the first Baron kill.

    @Column(nullable = false, unique = false)
    private boolean firstBlood = false; // Flag indicating whether or not the team scored the first blood.

    @Column(nullable = false, unique = false)
    private boolean firstTower = false; // Flag indicating whether or not the team destroyed the first tower.

    @Column(nullable = false, unique = false)
    private Integer baronKills = 0; // Number of times the team killed Baron.

    @Column(nullable = false, unique = false)
    private Integer riftHeraldKills = 0; // Number of times the team killed Rift Herald.

    @Column(nullable = false, unique = false)
    private Integer vilemawKills = 0; // Number of times the team killed Vilemaw.

    @Column(nullable = false, unique = false)
    private Integer inhibitorKills = 0; // Number of inhibitors the team destroyed.

    @Column(nullable = false, unique = false)
    private Integer towerKills = 0; // Number of towers the team destroyed.

    @Column(nullable = false, unique = false)
    private Integer dragonKills = 0; // Number of times the team killed Dragon.

    @Column(nullable = false, unique = false)
    private Integer dominionVictoryScore = 0; // For Dominion matches, specifies the points the team had at game end.

    @Column(nullable = false, unique = false)
    private boolean won = false; // CONVERTED FROM: String indicating whether or not the team won. There are only two values visibile in public match history. (Legal values: Fail, Win)

    @OneToOne
    private MatchGameTeam team; // CONVERTED FROM: int	100 for blue side. 200 for red side.

    @OneToMany
    private List<MatchGameTeamBan> bans; // If match queueId has a draft, contains banned champion data, otherwise empty.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isFirstBlood() {
        return firstBlood;
    }

    public void setFirstBlood(boolean firstBlood) {
        this.firstBlood = firstBlood;
    }

    public boolean isFirstTower() {
        return firstTower;
    }

    public void setFirstTower(boolean firstTower) {
        this.firstTower = firstTower;
    }

    public Integer getBaronKills() {
        return baronKills;
    }

    public void setBaronKills(Integer baronKills) {
        this.baronKills = baronKills;
    }

    public Integer getRiftHeraldKills() {
        return riftHeraldKills;
    }

    public void setRiftHeraldKills(Integer riftHeraldKills) {
        this.riftHeraldKills = riftHeraldKills;
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

    public Integer getDragonKills() {
        return dragonKills;
    }

    public void setDragonKills(Integer dragonKills) {
        this.dragonKills = dragonKills;
    }

    public Integer getDominionVictoryScore() {
        return dominionVictoryScore;
    }

    public void setDominionVictoryScore(Integer dominionVictoryScore) {
        this.dominionVictoryScore = dominionVictoryScore;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public MatchGameTeam getTeam() {
        return team;
    }

    public void setTeam(MatchGameTeam team) {
        this.team = team;
    }

    public List<MatchGameTeamBan> getBans() {
        return bans;
    }

    public void setBans(List<MatchGameTeamBan> bans) {
        this.bans = bans;
    }

    @Override
    public String toString() {
        return "MatchGameTeamStats{" +
                "id=" + id +
                ", firstDragon=" + firstDragon +
                ", firstInhibitor=" + firstInhibitor +
                ", firstRiftHerald=" + firstRiftHerald +
                ", firstBaron=" + firstBaron +
                ", firstBlood=" + firstBlood +
                ", firstTower=" + firstTower +
                ", baronKills=" + baronKills +
                ", riftHeraldKills=" + riftHeraldKills +
                ", vilemawKills=" + vilemawKills +
                ", inhibitorKills=" + inhibitorKills +
                ", towerKills=" + towerKills +
                ", dragonKills=" + dragonKills +
                ", dominionVictoryScore=" + dominionVictoryScore +
                ", won=" + won +
                ", team=" + team +
                ", bans=" + bans +
                '}';
    }
}
