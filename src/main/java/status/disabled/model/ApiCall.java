package status.disabled.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiCall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false, columnDefinition = "text")
    private String json;

    @OneToOne
    private ApiKey apiKey;

    public ApiCall(ApiKey apiKey, String json) {
        this.json = json;
        this.apiKey = apiKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public ApiKey getApiKey() {
        return apiKey;
    }

    public void setApiKey(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        return "ApiCall{" +
                "id=" + id +
                ", json='" + json + '\'' +
                ", apiKey=" + apiKey +
                '}';
    }
}
