package status.disabled.unknown.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueTier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String keyName;

    @Column(nullable = false, unique = false)
    private boolean scrapeable = true;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isScrapeable() {
        return scrapeable;
    }

    public void setScrapeable(boolean scrapeable) {
        this.scrapeable = scrapeable;
    }

    @Override
    public String toString() {
        return "LeagueTier{" +
                "id=" + id +
                ", keyName='" + keyName + '\'' +
                ", scrapeable=" + scrapeable +
                '}';
    }
}
