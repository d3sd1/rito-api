package com.onlol.fetcher.model;

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
    private GameVersion gameVersion;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private GameImage gameImage;

    @ManyToMany
    private List<GameItem> transformsInto; // parent items build with this one

    // GOLD DATA
    @Column(nullable = false, unique = false)
    private Integer baseGold = 0;

    @Column(nullable = false, unique = false)
    private Integer totalGold = 0;

    @Column(nullable = false, unique = false)
    private Integer sellGold = 0;

    @Column(nullable = false, unique = false)
    private Boolean purchasable = false;

    @ManyToMany
    private List<GameItemTag> gameItemTags;

    @ManyToMany
    private List<GameItemMap> gameItemMaps;

    @ManyToMany
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

    public GameVersion getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(GameVersion gameVersion) {
        this.gameVersion = gameVersion;
    }

    public List<GameItem> getTransformsInto() {
        return transformsInto;
    }

    public void setTransformsInto(List<GameItem> transformsInto) {
        this.transformsInto = transformsInto;
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

    public GameImage getGameImage() {
        return gameImage;
    }

    public void setGameImage(GameImage gameImage) {
        this.gameImage = gameImage;
    }

    @Override
    public String toString() {
        return "GameItem{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", version=" + gameVersion +
                ", gameImage=" + gameImage +
                ", transformsInto=" + transformsInto +
                ", baseGold=" + baseGold +
                ", totalGold=" + totalGold +
                ", sellGold=" + sellGold +
                ", purchasable=" + purchasable +
                ", gameItemTags=" + gameItemTags +
                ", gameItemMaps=" + gameItemMaps +
                ", gameItemStatModifiers=" + gameItemStatModifiers +
                '}';
    }
}
