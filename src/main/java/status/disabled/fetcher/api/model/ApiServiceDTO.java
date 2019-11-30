package status.disabled.fetcher.api.model;

import java.util.ArrayList;
import java.util.List;

/*
/lol/status/v3/shard-data
 */
public class ApiServiceDTO {
    private String status = "";
    private List<ApiIncidentDTO> incidents = new ArrayList<>();
    private String name = "";
    private String slug = ""; // depreceated

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ApiIncidentDTO> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<ApiIncidentDTO> incidents) {
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
