package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Champion {
    @Id
    @Column(nullable = false, unique = true)
    private Long champId;

    @Column(nullable = false, unique = false)
    private String keyName;

    @ManyToMany
    private List<ChampionTag> championTags;

    public Long getChampId() {
        return champId;
    }

    public void setChampId(Long champId) {
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
