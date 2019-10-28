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

    @OneToOne
    private GameImage gameImage;

    @ManyToMany
    private List<GameMode> gameModes;

    @Column(nullable = true, unique = false, columnDefinition = "text")
    private String cooldown;

    @Column(nullable = false, unique = false)
    private Float cooldownBurn = 0F;

    @Column(nullable = true, unique = false, columnDefinition = "text")
    private String cost;

    @Column(nullable = false, unique = false)
    private Float costBurn = 0F;

    @Column(nullable = true, unique = false, columnDefinition = "text")
    private String effect;

    @Column(nullable = true, unique = false, columnDefinition = "text")
    private String effectBurn;

    @Column(nullable = true, unique = false, columnDefinition = "text")
    private String range;

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

    public Float getRangeBurn() {
        return rangeBurn;
    }

    public void setRangeBurn(Float rangeBurn) {
        this.rangeBurn = rangeBurn;
    }

    public GameImage getGameImage() {
        return gameImage;
    }

    public void setGameImage(GameImage gameImage) {
        this.gameImage = gameImage;
    }

    public List<GameMode> getGameModes() {
        return gameModes;
    }

    public void setGameModes(List<GameMode> gameModes) {
        this.gameModes = gameModes;
    }

    public String getCooldown() {
        return cooldown;
    }

    public void setCooldown(String cooldown) {
        this.cooldown = cooldown;
    }

    public Float getCooldownBurn() {
        return cooldownBurn;
    }

    public void setCooldownBurn(Float cooldownBurn) {
        this.cooldownBurn = cooldownBurn;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Float getCostBurn() {
        return costBurn;
    }

    public void setCostBurn(Float costBurn) {
        this.costBurn = costBurn;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getEffectBurn() {
        return effectBurn;
    }

    public void setEffectBurn(String effectBurn) {
        this.effectBurn = effectBurn;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
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
                ", cooldown='" + cooldown + '\'' +
                ", cooldownBurn=" + cooldownBurn +
                ", cost='" + cost + '\'' +
                ", costBurn=" + costBurn +
                ", effect='" + effect + '\'' +
                ", effectBurn='" + effectBurn + '\'' +
                ", range='" + range + '\'' +
                '}';
    }
}
