package com.onlol.fetcher.api.riotModel;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class SampleItem {
    private String name;
    private String description;
    private String colloq;
    private String plaintext;
    private Integer[] into;
    private SampleImage image;
    private SampleItemGold gold;
    private String[] tags;
    private LinkedHashMap<Integer, Boolean> maps;
    private LinkedHashMap<String, Double> stats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColloq() {
        return colloq;
    }

    public void setColloq(String colloq) {
        this.colloq = colloq;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    public Integer[] getInto() {
        return into;
    }

    public void setInto(Integer[] into) {
        this.into = into;
    }

    public SampleImage getImage() {
        return image;
    }

    public void setImage(SampleImage image) {
        this.image = image;
    }

    public SampleItemGold getGold() {
        return gold;
    }

    public void setGold(SampleItemGold gold) {
        this.gold = gold;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public LinkedHashMap<Integer, Boolean> getMaps() {
        return maps;
    }

    public void setMaps(LinkedHashMap<Integer, Boolean> maps) {
        this.maps = maps;
    }

    public LinkedHashMap<String, Double> getStats() {
        return stats;
    }

    public void setStats(LinkedHashMap<String, Double> stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        return "SampleItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", colloq='" + colloq + '\'' +
                ", plaintext='" + plaintext + '\'' +
                ", into=" + Arrays.toString(into) +
                ", image=" + image +
                ", gold=" + gold +
                ", tags=" + Arrays.toString(tags) +
                ", maps=" + maps +
                ", stats=" + stats +
                '}';
    }
}
