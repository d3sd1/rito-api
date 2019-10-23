package com.onlol.fetcher.api.model;

import java.util.ArrayList;
import java.util.List;

/*
/lol/platform/v3/champion-rotations
 */
public class ApiChampionInfoDTO {
    private List<Integer> freeChampionIdsForNewPlayers = new ArrayList<>();
    private List<Integer> freeChampionIds = new ArrayList<>();
    private Integer maxNewPlayerLevel = 0;

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
