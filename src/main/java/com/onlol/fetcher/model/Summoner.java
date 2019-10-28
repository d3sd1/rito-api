package com.onlol.fetcher.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Summoner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = false)
    private Long riotRealId;

    @Column(nullable = false, unique = false)
    private String name;

    @Column(nullable = true, unique = false)
    private Integer profileIconId;

    @Column(nullable = true, unique = false)
    private Long revisionDate;

    @Column(nullable = true, unique = false)
    private Long summonerLevel;

    @Column(nullable = false, unique = false)
    private LocalDateTime lastTimeUpdated = LocalDateTime.now();

    @OneToOne
    private Region region;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getRiotRealId() {
        return riotRealId;
    }

    public void setRiotRealId(Long riotRealId) {
        this.riotRealId = riotRealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(Integer profileIconId) {
        this.profileIconId = profileIconId;
    }

    public Long getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Long revisionDate) {
        this.revisionDate = revisionDate;
    }

    public Long getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(Long summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public LocalDateTime getLastTimeUpdated() {
        return lastTimeUpdated;
    }

    public void setLastTimeUpdated(LocalDateTime lastTimeUpdated) {
        this.lastTimeUpdated = lastTimeUpdated;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Summoner{" +
                "id=" + id +
                ", riotRealId=" + riotRealId +
                ", name='" + name + '\'' +
                ", profileIconId=" + profileIconId +
                ", revisionDate=" + revisionDate +
                ", summonerLevel=" + summonerLevel +
                ", lastTimeUpdated=" + lastTimeUpdated +
                ", region=" + region +
                '}';
    }
}
