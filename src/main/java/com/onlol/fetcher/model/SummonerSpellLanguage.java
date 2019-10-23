package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummonerSpellLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = true, unique = false)
    private String name;

    @Column(nullable = true, unique = false, columnDefinition = "text")
    private String description;

    @Column(nullable = true, unique = false, columnDefinition = "text")
    private String tooltip;

    @Column(nullable = true, unique = false)
    private String costType;

    @Column(nullable = true, unique = false)
    private String resource;

    @OneToOne
    private Language language;

    @OneToOne
    private SummonerSpell summonerSpell;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public SummonerSpell getSummonerSpell() {
        return summonerSpell;
    }

    public void setSummonerSpell(SummonerSpell summonerSpell) {
        this.summonerSpell = summonerSpell;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
