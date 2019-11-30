package status.disabled.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameItemStatModifier {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne
    private GameItemStat gameItemStat;

    @OneToOne
    private GameItem gameItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameItemStat getGameItemStat() {
        return gameItemStat;
    }

    public void setGameItemStat(GameItemStat gameItemStat) {
        this.gameItemStat = gameItemStat;
    }

    public GameItem getGameItem() {
        return gameItem;
    }

    public void setGameItem(GameItem gameItem) {
        this.gameItem = gameItem;
    }

    @Override
    public String toString() {
        return "GameItemStatModifier{" +
                "id=" + id +
                ", gameItemStat=" + gameItemStat +
                ", gameItem=" + gameItem +
                '}';
    }
}
