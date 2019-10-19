package com.onlol.fetcher.api.model;

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
    private Version version;

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

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
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
}
