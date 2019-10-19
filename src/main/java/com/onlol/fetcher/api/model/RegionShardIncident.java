package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionShardIncident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = false)
    private boolean active;

    @Column(nullable = false, unique = false)
    private LocalDateTime createdAt;

    @OneToMany
    private List<RegionShardMessage> updates;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<RegionShardMessage> getUpdates() {
        return updates;
    }

    public void setUpdates(List<RegionShardMessage> updates) {
        this.updates = updates;
    }

    @Override
    public String toString() {
        return "RegionShardIncident{" +
                "id=" + id +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updates=" + updates +
                '}';
    }
}
