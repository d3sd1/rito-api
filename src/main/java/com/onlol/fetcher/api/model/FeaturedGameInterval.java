package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeaturedGameInterval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = false)
    private LocalDateTime timestamp;

    @OneToOne
    private Region region;

    @Column(nullable = false, unique = false)
    private Integer clientRefreshInterval = 0;

    @ManyToMany
    private List<MatchGame> matchGames;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Integer getClientRefreshInterval() {
        return clientRefreshInterval;
    }

    public void setClientRefreshInterval(Integer clientRefreshInterval) {
        this.clientRefreshInterval = clientRefreshInterval;
    }

    public List<MatchGame> getMatchGames() {
        return matchGames;
    }

    public void setMatchGames(List<MatchGame> matchGames) {
        this.matchGames = matchGames;
    }
}
