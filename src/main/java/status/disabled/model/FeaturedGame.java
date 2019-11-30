package status.disabled.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import status.disabled.fetcher.deserializer.FeaturedGameIntervalDeserializer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = FeaturedGameIntervalDeserializer.class)
public class FeaturedGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = false)
    private Long gameId; // TEMPORAL GAME ID. DO NOT USE @ONETOONE!!!!!

    @Column(nullable = false, unique = false)
    private LocalDateTime gameStartTime; // The game start time represented in epoch milliseconds

    @Column(nullable = false, unique = false)
    private long gameLength; // The amount of time in seconds that has passed since the game started

    @OneToOne
    private Region region;

    @OneToOne
    private GameMode gameMode;

    @OneToOne
    private GameMap gameMap;

    @OneToOne
    private GameType gameType;

    @OneToOne
    private GameQueue gameQueue;

    @Column(nullable = false, unique = false)
    private String observerKey;

    @ManyToMany
    private List<LiveGameParticipant> players;

    @ManyToMany
    private List<ChampionBan> bans;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(LocalDateTime gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public long getGameLength() {
        return gameLength;
    }

    public void setGameLength(long gameLength) {
        this.gameLength = gameLength;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public GameQueue getGameQueue() {
        return gameQueue;
    }

    public void setGameQueue(GameQueue gameQueue) {
        this.gameQueue = gameQueue;
    }

    public String getObserverKey() {
        return observerKey;
    }

    public void setObserverKey(String observerKey) {
        this.observerKey = observerKey;
    }

    public List<LiveGameParticipant> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<LiveGameParticipant> players) {
        this.players = players;
    }

    public List<ChampionBan> getBans() {
        return bans;
    }

    public void setBans(ArrayList<ChampionBan> bans) {
        this.bans = bans;
    }

    @Override
    public String toString() {
        return "FeaturedGame{" +
                "id=" + id +
                ", gameId=" + gameId +
                ", gameStartTime=" + gameStartTime +
                ", gameLength=" + gameLength +
                ", region=" + region +
                ", gameMode=" + gameMode +
                ", gameMap=" + gameMap +
                ", gameType=" + gameType +
                ", gameQueue=" + gameQueue +
                ", observerKey='" + observerKey + '\'' +
                ", players=" + players +
                ", bans=" + bans +
                '}';
    }
}
