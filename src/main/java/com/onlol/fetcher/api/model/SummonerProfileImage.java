package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummonerProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = false)
    private Integer profileImageId;

    @OneToOne
    private Version version;

    @OneToOne
    private GameImage gameImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public GameImage getGameImage() {
        return gameImage;
    }

    public void setGameImage(GameImage gameImage) {
        this.gameImage = gameImage;
    }

    public Integer getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(Integer profileImageId) {
        this.profileImageId = profileImageId;
    }

    @Override
    public String toString() {
        return "SummonerProfileImage{" +
                "id=" + id +
                ", profileImageId=" + profileImageId +
                ", version=" + version +
                ", gameImage=" + gameImage +
                '}';
    }
}
