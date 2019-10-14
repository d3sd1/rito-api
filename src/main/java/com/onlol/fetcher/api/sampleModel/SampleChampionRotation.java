package com.onlol.fetcher.api.sampleModel;

import java.util.List;

public class SampleChampionRotation {
    private List<Integer> freeChampionIdsForNewPlayers;
    private List<Integer> freeChampionIds;
    private Integer maxNewPlayerLevel;

    public List<Integer> getFreeChampionIdsForNewPlayers() {
        return freeChampionIdsForNewPlayers;
    }

    public void setFreeChampionIdsForNewPlayers(List<Integer> freeChampionIdsForNewPlayers) {
        this.freeChampionIdsForNewPlayers = freeChampionIdsForNewPlayers;
    }

    public List<Integer> getFreeChampionIds() {
        return freeChampionIds;
    }

    public void setFreeChampionIds(List<Integer> freeChampionIds) {
        this.freeChampionIds = freeChampionIds;
    }

    public Integer getMaxNewPlayerLevel() {
        return maxNewPlayerLevel;
    }

    public void setMaxNewPlayerLevel(Integer maxNewPlayerLevel) {
        this.maxNewPlayerLevel = maxNewPlayerLevel;
    }

    @Override
    public String toString() {
        return "SampleChampionRotation{" +
                "freeChampionIdsForNewPlayers=" + freeChampionIdsForNewPlayers +
                ", freeChampionIds=" + freeChampionIds +
                ", maxNewPlayerLevel=" + maxNewPlayerLevel +
                '}';
    }
}
