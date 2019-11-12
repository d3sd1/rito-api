package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveGameParticipantPerks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false)
    private Long perkStyle;

    @Column(nullable = false, unique = false)
    private Long perkSubStyle;

    @ManyToMany
    private List<Perk> perkIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPerkStyle() {
        return perkStyle;
    }

    public void setPerkStyle(Long perkStyle) {
        this.perkStyle = perkStyle;
    }

    public Long getPerkSubStyle() {
        return perkSubStyle;
    }

    public void setPerkSubStyle(Long perkSubStyle) {
        this.perkSubStyle = perkSubStyle;
    }

    public List<Perk> getPerkIds() {
        return perkIds;
    }

    public void setPerkIds(List<Perk> perkIds) {
        this.perkIds = perkIds;
    }

    @Override
    public String toString() {
        return "SummonerPerk{" +
                "id=" + id +
                ", perkStyle=" + perkStyle +
                ", perkSubStyle=" + perkSubStyle +
                ", perkIds=" + perkIds +
                '}';
    }
}
