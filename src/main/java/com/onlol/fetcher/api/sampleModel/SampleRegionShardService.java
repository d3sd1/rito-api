package com.onlol.fetcher.api.sampleModel;

import java.util.List;

public class SampleRegionShardService {
    private String status;
    private List<SampleRegionShardIncident> incidents;
    private String name;
    private String slug; // depreceated

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SampleRegionShardIncident> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<SampleRegionShardIncident> incidents) {
        this.incidents = incidents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "SampleShardService{" +
                "status='" + status + '\'' +
                ", incidents=" + incidents +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }
}
