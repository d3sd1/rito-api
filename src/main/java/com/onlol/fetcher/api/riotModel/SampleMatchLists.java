package com.onlol.fetcher.api.riotModel;

import java.util.List;

public class SampleMatchLists {
    private List<SampleMatchList> matches;
    private Long startIndex;
    private Long endIndex;
    private Long totalGames;

    public List<SampleMatchList> getMatches() {
        return matches;
    }

    public void setMatches(List<SampleMatchList> matches) {
        this.matches = matches;
    }

    public Long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Long startIndex) {
        this.startIndex = startIndex;
    }

    public Long getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Long endIndex) {
        this.endIndex = endIndex;
    }

    public Long getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(Long totalGames) {
        this.totalGames = totalGames;
    }

    @Override
    public String toString() {
        return "MatchLists{" +
                "matches=" + matches +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", totalGames=" + totalGames +
                '}';
    }
}
