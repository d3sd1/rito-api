package status.disabled.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionProxy {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String physicalRegion;

    @Column(nullable = false, unique = true)
    private String hostName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhysicalRegion() {
        return physicalRegion;
    }

    public void setPhysicalRegion(String physicalRegion) {
        this.physicalRegion = physicalRegion;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public String toString() {
        return "RegionProxy{" +
                "id=" + id +
                ", physicalRegion='" + physicalRegion + '\'' +
                ", host='" + hostName + '\'' +
                '}';
    }
}
