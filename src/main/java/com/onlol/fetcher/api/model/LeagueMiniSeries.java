package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueMiniSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String progress;

    @Column(nullable = false, unique = true)
    private Short target;

    @Column(nullable = false, unique = true)
    private Integer wins;

    @Column(nullable = false, unique = true)
    private Integer losses;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Short getTarget() {
        return target;
    }

    public void setTarget(Short target) {
        this.target = target;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    @Override
    public String toString() {
        return "LeagueMiniSeries{" +
                "id=" + id +
                ", progress='" + progress + '\'' +
                ", target=" + target +
                ", wins=" + wins +
                ", losses=" + losses +
                '}';
    }
}
