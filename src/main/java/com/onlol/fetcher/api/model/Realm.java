package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Realm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @OneToOne(optional = false)
    private Region region;

    @OneToOne
    private Version itemLastUpdate;

    @OneToOne
    private Version runeLastUpdate;

    @OneToOne
    private Version masteriesLastUpdate;

    @OneToOne
    private Version summonerLastUpdate;

    @OneToOne
    private Version championLastUpdate;

    @OneToOne
    private Version profileIconLastUpdate;

    @OneToOne
    private Version mapLastUpdate;

    @OneToOne
    private Version languageLastUpdate;

    @OneToOne
    private Version stickerLastUpdate;

    @OneToOne
    private Version patchLastUpdate;

    @OneToOne
    private Language defaultLanguage;

    @Column(nullable = true, unique = false)
    private String cdnUrl;

    @OneToOne
    private Version dataDdragonLastUpdate;

    @OneToOne
    private Version lg;

    @OneToOne
    private Version cssLastUpdate;

    @Column(nullable = true, unique = false)
    private Integer profileIconMax;

    @Column(nullable = true, unique = false)
    private String storeUrl;

    public Version getItemLastUpdate() {
        return itemLastUpdate;
    }

    public void setItemLastUpdate(Version itemLastUpdate) {
        this.itemLastUpdate = itemLastUpdate;
    }

    public Version getRuneLastUpdate() {
        return runeLastUpdate;
    }

    public void setRuneLastUpdate(Version runeLastUpdate) {
        this.runeLastUpdate = runeLastUpdate;
    }

    public Version getMasteriesLastUpdate() {
        return masteriesLastUpdate;
    }

    public void setMasteriesLastUpdate(Version masteriesLastUpdate) {
        this.masteriesLastUpdate = masteriesLastUpdate;
    }

    public Version getSummonerLastUpdate() {
        return summonerLastUpdate;
    }

    public void setSummonerLastUpdate(Version summonerLastUpdate) {
        this.summonerLastUpdate = summonerLastUpdate;
    }

    public Version getChampionLastUpdate() {
        return championLastUpdate;
    }

    public void setChampionLastUpdate(Version championLastUpdate) {
        this.championLastUpdate = championLastUpdate;
    }

    public Version getProfileIconLastUpdate() {
        return profileIconLastUpdate;
    }

    public void setProfileIconLastUpdate(Version profileIconLastUpdate) {
        this.profileIconLastUpdate = profileIconLastUpdate;
    }

    public Version getMapLastUpdate() {
        return mapLastUpdate;
    }

    public void setMapLastUpdate(Version mapLastUpdate) {
        this.mapLastUpdate = mapLastUpdate;
    }

    public Version getLanguageLastUpdate() {
        return languageLastUpdate;
    }

    public void setLanguageLastUpdate(Version languageLastUpdate) {
        this.languageLastUpdate = languageLastUpdate;
    }

    public Version getStickerLastUpdate() {
        return stickerLastUpdate;
    }

    public void setStickerLastUpdate(Version stickerLastUpdate) {
        this.stickerLastUpdate = stickerLastUpdate;
    }

    public Version getPatchLastUpdate() {
        return patchLastUpdate;
    }

    public void setPatchLastUpdate(Version patchLastUpdate) {
        this.patchLastUpdate = patchLastUpdate;
    }

    public Language getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(Language defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public String getCdnUrl() {
        return cdnUrl;
    }

    public void setCdnUrl(String cdnUrl) {
        this.cdnUrl = cdnUrl;
    }

    public Version getDataDdragonLastUpdate() {
        return dataDdragonLastUpdate;
    }

    public void setDataDdragonLastUpdate(Version dataDdragonLastUpdate) {
        this.dataDdragonLastUpdate = dataDdragonLastUpdate;
    }

    public Version getLg() {
        return lg;
    }

    public void setLg(Version lg) {
        this.lg = lg;
    }

    public Version getCssLastUpdate() {
        return cssLastUpdate;
    }

    public void setCssLastUpdate(Version cssLastUpdate) {
        this.cssLastUpdate = cssLastUpdate;
    }

    public Integer getProfileIconMax() {
        return profileIconMax;
    }

    public void setProfileIconMax(Integer profileIconMax) {
        this.profileIconMax = profileIconMax;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Realm{" +
                "id=" + id +
                ", region=" + region +
                ", itemLastUpdate=" + itemLastUpdate +
                ", runeLastUpdate=" + runeLastUpdate +
                ", masteriesLastUpdate=" + masteriesLastUpdate +
                ", summonerLastUpdate=" + summonerLastUpdate +
                ", championLastUpdate=" + championLastUpdate +
                ", profileIconLastUpdate=" + profileIconLastUpdate +
                ", mapLastUpdate=" + mapLastUpdate +
                ", languageLastUpdate=" + languageLastUpdate +
                ", stickerLastUpdate=" + stickerLastUpdate +
                ", patchLastUpdate=" + patchLastUpdate +
                ", defaultLanguage=" + defaultLanguage +
                ", cdnUrl='" + cdnUrl + '\'' +
                ", dataDdragonLastUpdate=" + dataDdragonLastUpdate +
                ", lg=" + lg +
                ", cssLastUpdate=" + cssLastUpdate +
                ", profileIconMax=" + profileIconMax +
                ", storeUrl='" + storeUrl + '\'' +
                '}';
    }
}
