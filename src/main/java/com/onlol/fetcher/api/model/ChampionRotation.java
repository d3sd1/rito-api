package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChampionRotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false)
    private String rotationDate;

    @OneToOne
    private Platform platform;

    @OneToOne
    private Champion champion;

    @Column(nullable = false, unique = false)
    private boolean forNewPlayers = false;

    @Column(nullable = false, unique = false)
    private Integer maxNewPlayerLevel = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRotationDate() {
        return rotationDate;
    }

    public void setRotationDate(String rotationDate) {
        this.rotationDate = rotationDate;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }

    public boolean isForNewPlayers() {
        return forNewPlayers;
    }

    public void setForNewPlayers(boolean forNewPlayers) {
        this.forNewPlayers = forNewPlayers;
    }

    public Integer getMaxNewPlayerLevel() {
        return maxNewPlayerLevel;
    }

    public void setMaxNewPlayerLevel(Integer maxNewPlayerLevel) {
        this.maxNewPlayerLevel = maxNewPlayerLevel;
    }

    @Override
    public String toString() {
        return "ChampionRotation{" +
                "id=" + id +
                ", rotationDate=" + rotationDate +
                ", platform=" + platform +
                ", champion=" + champion +
                ", forNewPlayers=" + forNewPlayers +
                ", maxNewPlayerLevel=" + maxNewPlayerLevel +
                '}';
    }
}
