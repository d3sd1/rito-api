package com.onlol.fetcher.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChampionBan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = false)
    private Short pickTurn;

    @OneToOne
    private Champion champion;

    @OneToOne
    private MatchGameTeam team;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getPickTurn() {
        return pickTurn;
    }

    public void setPickTurn(Short pickTurn) {
        this.pickTurn = pickTurn;
    }

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }

    public MatchGameTeam getTeam() {
        return team;
    }

    public void setTeam(MatchGameTeam team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "ChampionBan{" +
                "id=" + id +
                ", pickTurn=" + pickTurn +
                ", champion=" + champion +
                ", team=" + team +
                '}';
    }
}
