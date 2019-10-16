package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false)
    private Integer itemId;

    @OneToOne
    private Version version;

    @OneToMany
    private List<GameItem> transformsInto; // parent items build with this one

    // IMG DATA
    @Column(nullable = false, unique = false)
    private String imgFull = "";

    @Column(nullable = false, unique = false)
    private String imgSprite = "";

    @Column(nullable = false, unique = false)
    private String imgGroup = "";

    @Column(nullable = false, unique = false)
    private Integer imgX = 0;

    @Column(nullable = false, unique = false)
    private Integer imgY = 0;

    @Column(nullable = false, unique = false)
    private Integer imgW = 0;

    @Column(nullable = false, unique = false)
    private Integer imgH = 0;

    // GOLD DATA
    @Column(nullable = false, unique = false)
    private Integer baseGold = 0;

    @Column(nullable = false, unique = false)
    private Integer totalGold = 0;

    @Column(nullable = false, unique = false)
    private Integer sellGold = 0;

    @Column(nullable = false, unique = false)
    private Boolean purchasable = false;

    @OneToMany
    private List<GameItemTag> gameItemTags;

    @OneToMany
    private List<GameItemMap> gameItemMaps;

    @OneToMany
    private List<GameItemStatModifier> gameItemStatModifiers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public List<GameItem> getTransformsInto() {
        return transformsInto;
    }

    public void setTransformsInto(List<GameItem> transformsInto) {
        this.transformsInto = transformsInto;
    }

    public String getImgFull() {
        return imgFull;
    }

    public void setImgFull(String imgFull) {
        this.imgFull = imgFull;
    }

    public String getImgSprite() {
        return imgSprite;
    }

    public void setImgSprite(String imgSprite) {
        this.imgSprite = imgSprite;
    }

    public String getImgGroup() {
        return imgGroup;
    }

    public void setImgGroup(String imgGroup) {
        this.imgGroup = imgGroup;
    }

    public Integer getImgX() {
        return imgX;
    }

    public void setImgX(Integer imgX) {
        this.imgX = imgX;
    }

    public Integer getImgY() {
        return imgY;
    }

    public void setImgY(Integer imgY) {
        this.imgY = imgY;
    }

    public Integer getImgW() {
        return imgW;
    }

    public void setImgW(Integer imgW) {
        this.imgW = imgW;
    }

    public Integer getImgH() {
        return imgH;
    }

    public void setImgH(Integer imgH) {
        this.imgH = imgH;
    }

    public Integer getBaseGold() {
        return baseGold;
    }

    public void setBaseGold(Integer baseGold) {
        this.baseGold = baseGold;
    }

    public Integer getTotalGold() {
        return totalGold;
    }

    public void setTotalGold(Integer totalGold) {
        this.totalGold = totalGold;
    }

    public Integer getSellGold() {
        return sellGold;
    }

    public void setSellGold(Integer sellGold) {
        this.sellGold = sellGold;
    }

    public Boolean getPurchasable() {
        return purchasable;
    }

    public void setPurchasable(Boolean purchasable) {
        this.purchasable = purchasable;
    }

    public List<GameItemTag> getGameItemTags() {
        return gameItemTags;
    }

    public void setGameItemTags(List<GameItemTag> gameItemTags) {
        this.gameItemTags = gameItemTags;
    }

    public List<GameItemMap> getGameItemMaps() {
        return gameItemMaps;
    }

    public void setGameItemMaps(List<GameItemMap> gameItemMaps) {
        this.gameItemMaps = gameItemMaps;
    }

    public List<GameItemStatModifier> getGameItemStatModifiers() {
        return gameItemStatModifiers;
    }

    public void setGameItemStatModifiers(List<GameItemStatModifier> gameItemStatModifiers) {
        this.gameItemStatModifiers = gameItemStatModifiers;
    }
}
