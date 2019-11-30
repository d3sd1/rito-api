package status.disabled.unknown.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import status.disabled.unknown.fetcher.deserializer.LeagueDeserializer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonDeserialize(using = LeagueDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String riotId;

    @OneToOne
    private LeagueTier leagueTier;

    @OneToOne
    private GameQueueType gameQueueType;

    @Column(nullable = false, unique = false)
    private LocalDateTime lastTimeUpdated = LocalDateTime.now();

    @Column(nullable = false, unique = false)
    private Boolean retrieving = false;

    @Column(nullable = true, unique = false)
    private boolean disabled;

    @OneToOne
    private Region region;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRiotId() {
        return riotId;
    }

    public void setRiotId(String riotId) {
        this.riotId = riotId;
    }

    public LeagueTier getLeagueTier() {
        return leagueTier;
    }

    public void setLeagueTier(LeagueTier leagueTier) {
        this.leagueTier = leagueTier;
    }

    public GameQueueType getGameQueueType() {
        return gameQueueType;
    }

    public void setGameQueueType(GameQueueType gameQueueType) {
        this.gameQueueType = gameQueueType;
    }

    public LocalDateTime getLastTimeUpdated() {
        return lastTimeUpdated;
    }

    public void setLastTimeUpdated(LocalDateTime lastTimeUpdated) {
        this.lastTimeUpdated = lastTimeUpdated;
    }

    public Boolean getRetrieving() {
        return retrieving;
    }

    public void setRetrieving(Boolean retrieving) {
        this.retrieving = retrieving;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "League{" +
                "id=" + id +
                ", riotId='" + riotId + '\'' +
                ", leagueTier=" + leagueTier +
                ", gameQueueType=" + gameQueueType +
                ", lastTimeUpdated=" + lastTimeUpdated +
                ", retrieving=" + retrieving +
                ", disabled=" + disabled +
                ", region=" + region +
                '}';
    }
}
