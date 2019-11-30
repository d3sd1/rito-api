package status.disabled.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import status.disabled.fetcher.deserializer.SummonerTokenDeserializer;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = SummonerTokenDeserializer.class)
public class SummonerToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String summonerTokenId;

    @Column(nullable = true, unique = true)
    private String accountTokenId;

    @Column(nullable = true, unique = true)
    private String puuTokenId;

    @OneToOne(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private Summoner summoner;

    @OneToOne
    private ApiKey apiKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSummonerTokenId() {
        return summonerTokenId;
    }

    public void setSummonerTokenId(String summonerTokenId) {
        this.summonerTokenId = summonerTokenId;
    }

    public String getAccountTokenId() {
        return accountTokenId;
    }

    public void setAccountTokenId(String accountTokenId) {
        this.accountTokenId = accountTokenId;
    }

    public String getPuuTokenId() {
        return puuTokenId;
    }

    public void setPuuTokenId(String puuTokenId) {
        this.puuTokenId = puuTokenId;
    }

    public Summoner getSummoner() {
        return summoner;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

    public ApiKey getApiKey() {
        return apiKey;
    }

    public void setApiKey(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        return "SummonerToken{" +
                "id=" + id +
                ", summonerTokenId='" + summonerTokenId + '\'' +
                ", accountTokenId='" + accountTokenId + '\'' +
                ", puuTokenId='" + puuTokenId + '\'' +
                ", summoner=" + summoner +
                ", apiKey=" + apiKey +
                '}';
    }
}

