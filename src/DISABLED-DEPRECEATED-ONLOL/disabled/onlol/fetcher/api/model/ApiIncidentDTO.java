package status.disabled.onlol.fetcher.api.model;

import java.util.ArrayList;
import java.util.List;

/*
/lol/status/v3/shard-data
 */
public class ApiIncidentDTO {
    private boolean active = false;
    private String created_at = "";
    private Long id = 0L;
    private List<ApiMessageDTO> updates = new ArrayList<>();

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

    public List<ApiMessageDTO> getUpdates() {
        return updates;
    }

    public void setUpdates(List<ApiMessageDTO> updates) {
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
