package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.onlol.fetcher.deserializer.LeagueDeserializer;

import javax.persistence.*;

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

    @Override
    public String toString() {
        return "League{" +
                "id=" + id +
                ", riotId='" + riotId + '\'' +
                ", leagueTier=" + leagueTier +
                ", gameQueueType=" + gameQueueType +
                '}';
    }
}
