package com.onlol.fetcher.api.riotModel;

import java.util.List;

public class SampleFeaturedGames {
    private Integer clientRefreshInterval;
    private List<SampleFeaturedGameInfo> gameList;

    public Integer getClientRefreshInterval() {
        return clientRefreshInterval;
    }

    public void setClientRefreshInterval(Integer clientRefreshInterval) {
        this.clientRefreshInterval = clientRefreshInterval;
    }

    public List<SampleFeaturedGameInfo> getGameList() {
        return gameList;
    }

    public void setGameList(List<SampleFeaturedGameInfo> gameList) {
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
