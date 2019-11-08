package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.onlol.fetcher.deserializer.FeaturedGameIntervalDeserializer;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = FeaturedGameIntervalDeserializer.class)
public class FeaturedGameParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @OneToOne
    private Summoner summoner;

    @OneToOne
    private Champion champion;

    @Column(nullable = false, unique = false)
    private boolean bot;

    @OneToOne
    private SummonerSpell summonerSpell1;

    @OneToOne
    private SummonerSpell summonerSpell2;

    @OneToOne
    private MatchGameTeam team;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Summoner getSummoner() {
        return summoner;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public SummonerSpell getSummonerSpell1() {
        return summonerSpell1;
    }

    public void setSummonerSpell1(SummonerSpell summonerSpell1) {
        this.summonerSpell1 = summonerSpell1;
    }

    public SummonerSpell getSummonerSpell2() {
        return summonerSpell2;
    }

    public void setSummonerSpell2(SummonerSpell summonerSpell2) {
        this.summonerSpell2 = summonerSpell2;
    }

    public MatchGameTeam getTeam() {
        return team;
    }

    public void setTeam(MatchGameTeam team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "FeaturedGameParticipant{" +
                "id=" + id +
                ", summoner=" + summoner +
                ", champion=" + champion +
                ", bot=" + bot +
                ", summonerSpell1=" + summonerSpell1 +
                ", summonerSpell2=" + summonerSpell2 +
                ", team=" + team +
                '}';
    }
}
