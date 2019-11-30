package status.disabled.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = false) // DUE TO NA!!
    private String serviceRegion;

    @Column(nullable = false, unique = true)
    private String servicePlatform;

    @Column(nullable = false, unique = false) // DUE TO NA!!
    private String hostName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceRegion() {
        return serviceRegion;
    }

    public void setServiceRegion(String serviceRegion) {
        this.serviceRegion = serviceRegion;
    }

    public String getServicePlatform() {
        return servicePlatform;
    }

    public void setServicePlatform(String servicePlatform) {
        this.servicePlatform = servicePlatform;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", serviceRegion='" + serviceRegion + '\'' +
                ", servicePlatform='" + servicePlatform + '\'' +
                ", host='" + hostName + '\'' +
                '}';
    }
}
