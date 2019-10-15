package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameMap {
    @Id
    @Column(nullable = false, unique = true)
    private Integer mapId;

    @Column(nullable = true, unique = false)
    private String mapName;

    @Column(nullable = true, unique = false)
    private String notes;

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "GameMap{" +
                "mapId=" + mapId +
                ", mapName='" + mapName + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
