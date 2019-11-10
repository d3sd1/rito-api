package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.onlol.fetcher.deserializer.FeaturedGameIntervalDeserializer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = FeaturedGameIntervalDeserializer.class)
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<FeaturedGame> featuredGames;

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

    public List<FeaturedGame> getFeaturedGames() {
        return featuredGames;
    }

    public void setFeaturedGames(List<FeaturedGame> featuredGames) {
        this.featuredGames = featuredGames;
    }

    @Override
    public String toString() {
        return "FeaturedGameInterval{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", region=" + region +
                ", clientRefreshInterval=" + clientRefreshInterval +
                ", featuredGames=" + featuredGames +
                '}';
    }
}
