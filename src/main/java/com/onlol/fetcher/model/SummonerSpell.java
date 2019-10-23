package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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

    /*
        @Column(nullable = true, unique = false)
        private List<GameMode> gameModes;
     TODO: hacer funcionar esto


        @Column(nullable = true, unique = false)
        private List<Integer> cooldown;

        @Column(nullable = false, unique = false)
        private Float cooldownBurn = 0F;

        @Column(nullable = true, unique = false)
        private List<Integer> cost;

        @Column(nullable = false, unique = false)
        private Float costBurn = 0F;

        @Column(nullable = true, unique = false)
        private List<Integer> effect;

        @Column(nullable = true, unique = false)
        private List<Float> effectBurn;

        @Column(nullable = true, unique = false)
        private List<Integer> range;
    */
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
                '}';
    }
}
