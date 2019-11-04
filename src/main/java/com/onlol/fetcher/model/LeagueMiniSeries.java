package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueMiniSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private Summoner summoner;

    @Column(nullable = false, unique = true)
    private String progress = "";

    @Column(nullable = false, unique = true)
    private Short target = 0;

    @Column(nullable = false, unique = true)
    private Integer wins = 0;

    @Column(nullable = false, unique = true)
    private Integer losses = 0;

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

    public Summoner getSummoner() {
        return summoner;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

    @Override
    public String toString() {
        return "LeagueMiniSeries{" +
                "id=" + id +
                ", summoner=" + summoner +
                ", progress='" + progress + '\'' +
                ", target=" + target +
                ", wins=" + wins +
                ", losses=" + losses +
                '}';
    }
}
