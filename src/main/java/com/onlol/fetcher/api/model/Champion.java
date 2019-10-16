package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Champion {
    @Id
    @Column(nullable = false, unique = true)
    private Integer champId;

    @Column(nullable = false, unique = false)
    private String keyName;

    @OneToMany
    private List<ChampionTag> championTags;

    public Integer getChampId() {
        return champId;
    }

    public void setChampId(Integer champId) {
        this.champId = champId;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public List<ChampionTag> getChampionTags() {
        return championTags;
    }

    public void setChampionTags(List<ChampionTag> championTags) {
        this.championTags = championTags;
    }

    @Override
    public String toString() {
        return "Champion{" +
                "champId=" + champId +
                ", keyName='" + keyName + '\'' +
                ", championTags=" + championTags +
                '}';
    }
}
