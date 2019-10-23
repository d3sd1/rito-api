package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummonerSpell {
    @Id
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String keyName;

    @OneToOne
    private GameVersion gameVersion;

    @Column(nullable = false, unique = false)
    private Short maxrank = 0;

    @Column(nullable = false, unique = false)
    private Short summonerLevel = 0;

    @Column(nullable = false, unique = false)
    private Integer maxammo = 0;

    @Column(nullable = false, unique = false)
    private Float rangeBurn = 0F;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private GameImage gameImage;

    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<GameMode> gameModes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Integer> cooldown;

    @Column(nullable = false, unique = false)
    private Float cooldownBurn = 0F;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Integer> cost;

    @Column(nullable = false, unique = false)
    private Float costBurn = 0F;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Integer> effect;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Float> effectBurn;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Integer> range;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public GameVersion getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(GameVersion gameVersion) {
        this.gameVersion = gameVersion;
    }

    public Short getMaxrank() {
        return maxrank;
    }

    public void setMaxrank(Short maxrank) {
        this.maxrank = maxrank;
    }

    public Short getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(Short summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public Integer getMaxammo() {
        return maxammo;
    }

    public void setMaxammo(Integer maxammo) {
        this.maxammo = maxammo;
    }

    public GameImage getGameImage() {
        return gameImage;
    }

    public void setGameImage(GameImage gameImage) {
        this.gameImage = gameImage;
    }

    public Float getRangeBurn() {
        return rangeBurn;
    }

    public void setRangeBurn(Float rangeBurn) {
        this.rangeBurn = rangeBurn;
    }

    public List<GameMode> getGameModes() {
        return gameModes;
    }

    public void setGameModes(List<GameMode> gameModes) {
        this.gameModes = gameModes;
    }

    public List<Integer> getCooldown() {
        return cooldown;
    }

    public void setCooldown(List<Integer> cooldown) {
        this.cooldown = cooldown;
    }

    public Float getCooldownBurn() {
        return cooldownBurn;
    }

    public void setCooldownBurn(Float cooldownBurn) {
        this.cooldownBurn = cooldownBurn;
    }

    public List<Integer> getCost() {
        return cost;
    }

    public void setCost(List<Integer> cost) {
        this.cost = cost;
    }

    public Float getCostBurn() {
        return costBurn;
    }

    public void setCostBurn(Float costBurn) {
        this.costBurn = costBurn;
    }

    public List<Integer> getEffect() {
        return effect;
    }

    public void setEffect(List<Integer> effect) {
        this.effect = effect;
    }

    public List<Float> getEffectBurn() {
        return effectBurn;
    }

    public void setEffectBurn(List<Float> effectBurn) {
        this.effectBurn = effectBurn;
    }

    public List<Integer> getRange() {
        return range;
    }

    public void setRange(List<Integer> range) {
        this.range = range;
    }

    @Override
    public String toString() {
        return "SummonerSpell{" +
                "id=" + id +
                ", keyName='" + keyName + '\'' +
                ", gameVersion=" + gameVersion +
                ", maxrank=" + maxrank +
                ", summonerLevel=" + summonerLevel +
                ", maxammo=" + maxammo +
                ", rangeBurn=" + rangeBurn +
                ", gameImage=" + gameImage +
                ", gameModes=" + gameModes +
                ", cooldown=" + cooldown +
                ", cooldownBurn=" + cooldownBurn +
                ", cost=" + cost +
                ", costBurn=" + costBurn +
                ", effect=" + effect +
                ", effectBurn=" + effectBurn +
                ", range=" + range +
                '}';
    }
}
