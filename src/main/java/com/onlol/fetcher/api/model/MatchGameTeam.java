package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchGameTeam {
    @Id
    @Column(nullable = false, unique = true)
    private Integer teamId;

    @Column(nullable = true, unique = false)
    private String description;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MatchGameTeam{" +
                "teamId=" + teamId +
                ", description='" + description + '\'' +
                '}';
    }
}
