package status.disabled.unknown.fetcher.api.model;

import java.util.ArrayList;

/*
/lol/league/v4/challengerleagues/by-queue/{queue}
/lol/league/v4/grandmasterleagues/by-queue/{queue}
/lol/league/v4/masterleagues/by-queue/{queue}
/lol/league/v4/leagues/{leagueId}
 */
public class ApiLeagueListDTO {
    private String leagueId = "";
    private String tier = "";
    private ArrayList<ApiLeagueItemDTO> entries = new ArrayList<>();
    private String queue = "";
    private String name = "";

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

    public ArrayList<ApiLeagueItemDTO> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<ApiLeagueItemDTO> entries) {
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
