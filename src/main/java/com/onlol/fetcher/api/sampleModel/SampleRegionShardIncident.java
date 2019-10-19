package com.onlol.fetcher.api.sampleModel;

import java.util.List;

public class SampleRegionShardIncident {
    private boolean active;
    private String created_at;
    private Long id;
    private List<SampleRegionShardMessage> updates;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SampleRegionShardMessage> getUpdates() {
        return updates;
    }

    public void setUpdates(List<SampleRegionShardMessage> updates) {
        this.updates = updates;
    }

    @Override
    public String toString() {
        return "SampleShardIncident{" +
                "active=" + active +
                ", created_at='" + created_at + '\'' +
                ", id=" + id +
                ", updates=" + updates +
                '}';
    }
}
