package status.disabled.model;

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
    private GameVersion itemLastUpdate;

    @OneToOne
    private GameVersion runeLastUpdate;

    @OneToOne
    private GameVersion masteriesLastUpdate;

    @OneToOne
    private GameVersion summonerLastUpdate;

    @OneToOne
    private GameVersion championLastUpdate;

    @OneToOne
    private GameVersion profileIconLastUpdate;

    @OneToOne
    private GameVersion mapLastUpdate;

    @OneToOne
    private GameVersion languageLastUpdate;

    @OneToOne
    private GameVersion stickerLastUpdate;

    @OneToOne
    private GameVersion patchLastUpdate;

    @OneToOne
    private Language defaultLanguage;

    @Column(nullable = true, unique = false)
    private String cdnUrl;

    @OneToOne
    private GameVersion dataDdragonLastUpdate;

    @OneToOne
    private GameVersion lg;

    @OneToOne
    private GameVersion cssLastUpdate;

    @Column(nullable = true, unique = false)
    private Integer profileIconMax;

    @Column(nullable = true, unique = false)
    private String storeUrl;

    public GameVersion getItemLastUpdate() {
        return itemLastUpdate;
    }

    public void setItemLastUpdate(GameVersion itemLastUpdate) {
        this.itemLastUpdate = itemLastUpdate;
    }

    public GameVersion getRuneLastUpdate() {
        return runeLastUpdate;
    }

    public void setRuneLastUpdate(GameVersion runeLastUpdate) {
        this.runeLastUpdate = runeLastUpdate;
    }

    public GameVersion getMasteriesLastUpdate() {
        return masteriesLastUpdate;
    }

    public void setMasteriesLastUpdate(GameVersion masteriesLastUpdate) {
        this.masteriesLastUpdate = masteriesLastUpdate;
    }

    public GameVersion getSummonerLastUpdate() {
        return summonerLastUpdate;
    }

    public void setSummonerLastUpdate(GameVersion summonerLastUpdate) {
        this.summonerLastUpdate = summonerLastUpdate;
    }

    public GameVersion getChampionLastUpdate() {
        return championLastUpdate;
    }

    public void setChampionLastUpdate(GameVersion championLastUpdate) {
        this.championLastUpdate = championLastUpdate;
    }

    public GameVersion getProfileIconLastUpdate() {
        return profileIconLastUpdate;
    }

    public void setProfileIconLastUpdate(GameVersion profileIconLastUpdate) {
        this.profileIconLastUpdate = profileIconLastUpdate;
    }

    public GameVersion getMapLastUpdate() {
        return mapLastUpdate;
    }

    public void setMapLastUpdate(GameVersion mapLastUpdate) {
        this.mapLastUpdate = mapLastUpdate;
    }

    public GameVersion getLanguageLastUpdate() {
        return languageLastUpdate;
    }

    public void setLanguageLastUpdate(GameVersion languageLastUpdate) {
        this.languageLastUpdate = languageLastUpdate;
    }

    public GameVersion getStickerLastUpdate() {
        return stickerLastUpdate;
    }

    public void setStickerLastUpdate(GameVersion stickerLastUpdate) {
        this.stickerLastUpdate = stickerLastUpdate;
    }

    public GameVersion getPatchLastUpdate() {
        return patchLastUpdate;
    }

    public void setPatchLastUpdate(GameVersion patchLastUpdate) {
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

    public GameVersion getDataDdragonLastUpdate() {
        return dataDdragonLastUpdate;
    }

    public void setDataDdragonLastUpdate(GameVersion dataDdragonLastUpdate) {
        this.dataDdragonLastUpdate = dataDdragonLastUpdate;
    }

    public GameVersion getLg() {
        return lg;
    }

    public void setLg(GameVersion lg) {
        this.lg = lg;
    }

    public GameVersion getCssLastUpdate() {
        return cssLastUpdate;
    }

    public void setCssLastUpdate(GameVersion cssLastUpdate) {
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
