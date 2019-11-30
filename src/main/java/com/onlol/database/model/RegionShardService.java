package com.onlol.database.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionShardService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = false)
    private String name;

    @Column(nullable = false, unique = false)
    private boolean online;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<RegionShardIncident> incidents;

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

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public List<RegionShardIncident> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<RegionShardIncident> incidents) {
        this.incidents = incidents;
    }

    @Override
    public String toString() {
        return "RegionShardService{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", online=" + online +
                ", incidents=" + incidents +
                '}';
    }
}
