package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = true, unique = true)
    private Integer mapId;

    @Column(nullable = true, unique = false)
    private String mapName; // Must be string since name is not unique and it has no ID so it could be several map's

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GameMap{" +
                "id=" + id +
                ", mapId=" + mapId +
                ", mapName='" + mapName + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
