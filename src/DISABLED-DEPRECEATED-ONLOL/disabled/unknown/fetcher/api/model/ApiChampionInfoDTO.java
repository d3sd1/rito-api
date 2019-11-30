package status.disabled.unknown.fetcher.api.model;

import java.util.ArrayList;
import java.util.List;

/*
/lol/platform/v3/champion-rotations
 */
public class ApiChampionInfoDTO {
    private List<Long> freeChampionIdsForNewPlayers = new ArrayList<>();
    private List<Long> freeChampionIds = new ArrayList<>();
    private Integer maxNewPlayerLevel = 0;

    public List<Long> getFreeChampionIdsForNewPlayers() {
        return freeChampionIdsForNewPlayers;
    }

    public void setFreeChampionIdsForNewPlayers(List<Long> freeChampionIdsForNewPlayers) {
        this.freeChampionIdsForNewPlayers = freeChampionIdsForNewPlayers;
    }

    public List<Long> getFreeChampionIds() {
        return freeChampionIds;
    }

    public void setFreeChampionIds(List<Long> freeChampionIds) {
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
