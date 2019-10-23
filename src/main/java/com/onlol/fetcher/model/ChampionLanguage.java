package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChampionLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @OneToOne
    private Champion champion;

    @OneToOne
    private Language language;

    @OneToOne
    private GameVersion gameVersion;

    @Column(nullable = true, unique = false)
    private String partype;

    @Column(nullable = true, unique = false, columnDefinition = "text")
    private String blurb;

    @Column(nullable = true, unique = false)
    private String title;

    @Column(nullable = true, unique = false)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getPartype() {
        return partype;
    }

    public void setPartype(String partype) {
        this.partype = partype;
    }

    public GameVersion getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(GameVersion gameVersion) {
        this.gameVersion = gameVersion;
    }

    @Override
    public String toString() {
        return "ChampionLanguage{" +
                "id=" + id +
                ", champion=" + champion +
                ", language=" + language +
                ", version=" + gameVersion +
                ", partype='" + partype + '\'' +
                ", blurb='" + blurb + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
