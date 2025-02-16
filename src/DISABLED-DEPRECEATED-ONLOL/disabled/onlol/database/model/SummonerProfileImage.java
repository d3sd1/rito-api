package status.disabled.onlol.database.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummonerProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = false)
    private Integer profileImageId;

    @OneToOne
    private GameVersion gameVersion;

    @OneToOne
    private GameImage gameImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GameVersion getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(GameVersion gameVersion) {
        this.gameVersion = gameVersion;
    }

    public GameImage getGameImage() {
        return gameImage;
    }

    public void setGameImage(GameImage gameImage) {
        this.gameImage = gameImage;
    }

    public Integer getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(Integer profileImageId) {
        this.profileImageId = profileImageId;
    }

    @Override
    public String toString() {
        return "SummonerProfileImage{" +
                "id=" + id +
                ", profileImageId=" + profileImageId +
                ", version=" + gameVersion +
                ", gameImage=" + gameImage +
                '}';
    }
}
