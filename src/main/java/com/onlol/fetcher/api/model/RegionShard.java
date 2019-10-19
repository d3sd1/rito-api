package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionShard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @OneToOne
    private Version version;

    @OneToOne
    private Region region;

    @Column(nullable = true, unique = false)
    private String hostName;

    @ManyToMany
    private List<Language> languages;

    @Column(nullable = true, unique = false)
    private String name;

    @Column(nullable = true, unique = false)
    private String regionTag;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<RegionShardService> regionShardServices;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionTag() {
        return regionTag;
    }

    public void setRegionTag(String regionTag) {
        this.regionTag = regionTag;
    }

    public List<RegionShardService> getRegionShardServices() {
        return regionShardServices;
    }

    public void setRegionShardServices(List<RegionShardService> regionShardServices) {
        this.regionShardServices = regionShardServices;
    }

    @Override
    public String toString() {
        return "RegionShard{" +
                "id=" + id +
                ", version=" + version +
                ", region=" + region +
                ", hostName='" + hostName + '\'' +
                ", languages=" + languages +
                ", name='" + name + '\'' +
                ", regionTag='" + regionTag + '\'' +
                ", regionShardServices=" + regionShardServices +
                '}';
    }
}
