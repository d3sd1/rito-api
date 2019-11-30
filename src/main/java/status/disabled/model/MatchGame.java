package status.disabled.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false)
    private Long gameId;

    @Column(nullable = false, unique = false)
    private boolean retrieved = false;

    @Column(nullable = false, unique = false)
    private boolean retrieving = false;

    @Column(nullable = false, unique = false)
    private boolean expired = false; // if api returns 404

    @OneToOne
    private GameSeason gameSeason;

    @OneToOne
    private GameQueue gameQueue;

    @Column(nullable = true, unique = false)
    private String gameVersion; // major.minor version

    @OneToOne
    private Region region;

    @OneToOne
    private GameMap gameMap;

    @OneToOne
    private GameMode gameMode;

    @OneToOne
    private GameType gameType;

    @Column(nullable = false, unique = false)
    private Long gameDuration = 0L; // Match duration in seconds

    @Column(nullable = true, unique = false)
    private LocalDateTime gameCreation; // Designates the timestamp when champion select ended and the loading screen appeared, NOT when the game timer was at 0:00.

    @OneToMany
    private List<MatchGameTeamStats> matchGameTeam;

    @OneToMany
    private List<MatchGameParticipant> matchGameParticipants;

    @OneToMany
    private List<MatchGameTeamStats> matchGameTeamStats;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public boolean isRetrieved() {
        return retrieved;
    }

    public void setRetrieved(boolean retrieved) {
        this.retrieved = retrieved;
    }

    public boolean isRetrieving() {
        return retrieving;
    }

    public void setRetrieving(boolean retrieving) {
        this.retrieving = retrieving;
    }

    public GameSeason getGameSeason() {
        return gameSeason;
    }

    public void setGameSeason(GameSeason gameSeason) {
        this.gameSeason = gameSeason;
    }

    public GameQueue getGameQueue() {
        return gameQueue;
    }

    public void setGameQueue(GameQueue gameQueue) {
        this.gameQueue = gameQueue;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Long getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(Long gameDuration) {
        this.gameDuration = gameDuration;
    }

    public LocalDateTime getGameCreation() {
        return gameCreation;
    }

    public void setGameCreation(LocalDateTime gameCreation) {
        this.gameCreation = gameCreation;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public List<MatchGameTeamStats> getMatchGameTeam() {
        return matchGameTeam;
    }

    public void setMatchGameTeam(List<MatchGameTeamStats> matchGameTeamStats) {
        this.matchGameTeam = matchGameTeamStats;
    }

    public List<MatchGameParticipant> getMatchGameParticipants() {
        return matchGameParticipants;
    }

    public void setMatchGameParticipants(List<MatchGameParticipant> matchGameParticipants) {
        this.matchGameParticipants = matchGameParticipants;
    }

    public List<MatchGameTeamStats> getMatchGameTeamStats() {
        return matchGameTeamStats;
    }

    public void setMatchGameTeamStats(List<MatchGameTeamStats> matchGameTeamStats) {
        this.matchGameTeamStats = matchGameTeamStats;
    }

    @Override
    public String toString() {
        return "MatchGame{" +
                "id=" + id +
                ", gameId=" + gameId +
                ", retrieved=" + retrieved +
                ", retrieving=" + retrieving +
                ", expired=" + expired +
                ", gameSeason=" + gameSeason +
                ", gameQueue=" + gameQueue +
                ", gameVersion='" + gameVersion + '\'' +
                ", region=" + region +
                ", gameMap=" + gameMap +
                ", gameMode=" + gameMode +
                ", gameType=" + gameType +
                ", gameDuration=" + gameDuration +
                ", gameCreation=" + gameCreation +
                ", matchGameTeam=" + matchGameTeam +
                ", matchGameParticipants=" + matchGameParticipants +
                ", matchGameTeamStats=" + matchGameTeamStats +
                '}';
    }
}
