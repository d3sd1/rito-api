package status.disabled.unknown.fetcher.api.model;

import java.util.ArrayList;
import java.util.List;

/*
/lol/spectator/v4/featured-games
 */
public class ApiFeaturedGamesDTO {
    private Integer clientRefreshInterval = 0; // The suggested interval to wait before requesting FeaturedGames again
    private List<ApiFeaturedGameInfoDTO> gameList = new ArrayList<>(); // The list of featured games

    public Integer getClientRefreshInterval() {
        return clientRefreshInterval;
    }

    public void setClientRefreshInterval(Integer clientRefreshInterval) {
        this.clientRefreshInterval = clientRefreshInterval;
    }

    public List<ApiFeaturedGameInfoDTO> getGameList() {
        return gameList;
    }

    public void setGameList(List<ApiFeaturedGameInfoDTO> gameList) {
        this.gameList = gameList;
    }

    @Override
    public String toString() {
        return "SampleFeaturedGames{" +
                "clientRefreshInterval=" + clientRefreshInterval +
                ", gameList=" + gameList +
                '}';
    }
}
