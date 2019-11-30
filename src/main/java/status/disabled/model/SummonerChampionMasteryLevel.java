package status.disabled.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import status.disabled.fetcher.deserializer.SummonerChampionMasteryDeserializer;

import javax.persistence.*;

@Entity
@JsonDeserialize(using = SummonerChampionMasteryDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummonerChampionMasteryLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne
    private Summoner summoner;

    @Column(nullable = false, unique = false)
    private Integer level = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Summoner getSummoner() {
        return summoner;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "SummonerChampionMasteryLevel{" +
                "id=" + id +
                ", summoner=" + summoner +
                ", level=" + level +
                '}';
    }
}
