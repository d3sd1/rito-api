package com.onlol.fetcher.api.riotModel;

import java.util.ArrayList;

public class SampleSummonerLeagueList {
    private String leagueId;
    private String tier;
    private ArrayList<SampleSummonerLeague> entries = new ArrayList<>();
    private String queue;
    private String name;

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public ArrayList<SampleSummonerLeague> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<SampleSummonerLeague> entries) {
        this.entries = entries;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SampleSummonerLeagueList{" +
                "leagueId='" + leagueId + '\'' +
                ", tier='" + tier + '\'' +
                ", entries=" + entries +
                ", queue='" + queue + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
