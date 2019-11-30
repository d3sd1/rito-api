package status.disabled.unknown.fetcher.api.model;

import java.util.ArrayList;
import java.util.List;

/*
CONFLICTS WITH ApiObserverParticipantDTO (renamed)
/lol/match/v4/matches/{matchId}
 */
public class ApiMatchParticipantDTO {
    private ApiParticipantStatsDTO stats = new ApiParticipantStatsDTO(); // Participant statistics.
    private Integer participantId = 0;
    private List<ApiRuneDTO> runes = new ArrayList<>(); // List of legacy Rune information. Not included for matches played with Runes Reforged.
    private ApiParticipantTimelineDTO timeline = new ApiParticipantTimelineDTO(); // Participant timeline data.
    private Integer teamId = 0; // int	100 for blue side. 200 for red side.
    private Integer championId = 0;
    private Integer spell1Id = 0; // First Summoner Spell id.
    private Integer spell2Id = 0; // Second Summoner Spell id.
    private String highestAchievedSeasonTier = ""; // Highest ranked tier achieved for the previous season in a specific subset of queueIds, if any, otherwise null. Used to display border in game loading screen. Please refer to the Ranked Info documentation. (Legal values: CHALLENGER, MASTER, DIAMOND, PLATINUM, GOLD, SILVER, BRONZE, UNRANKED)
    private List<ApiMasteryDTO> masteries = new ArrayList<>(); // List of legacy Mastery information. Not included for matches played with Runes Reforged.

    public ApiParticipantStatsDTO getStats() {
        return stats;
    }

    public void setStats(ApiParticipantStatsDTO stats) {
        this.stats = stats;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public List<ApiRuneDTO> getRunes() {
        return runes;
    }

    public void setRunes(List<ApiRuneDTO> runes) {
        this.runes = runes;
    }

    public ApiParticipantTimelineDTO getTimeline() {
        return timeline;
    }

    public void setTimeline(ApiParticipantTimelineDTO timeline) {
        this.timeline = timeline;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getChampionId() {
        return championId;
    }

    public void setChampionId(Integer championId) {
        this.championId = championId;
    }

    public Integer getSpell1Id() {
        return spell1Id;
    }

    public void setSpell1Id(Integer spell1Id) {
        this.spell1Id = spell1Id;
    }

    public Integer getSpell2Id() {
        return spell2Id;
    }

    public void setSpell2Id(Integer spell2Id) {
        this.spell2Id = spell2Id;
    }

    public String getHighestAchievedSeasonTier() {
        return highestAchievedSeasonTier;
    }

    public void setHighestAchievedSeasonTier(String highestAchievedSeasonTier) {
        this.highestAchievedSeasonTier = highestAchievedSeasonTier;
    }

    public List<ApiMasteryDTO> getMasteries() {
        return masteries;
    }

    public void setMasteries(List<ApiMasteryDTO> masteries) {
        this.masteries = masteries;
    }

    @Override
    public String toString() {
        return "SampleParticipant{" +
                "stats=" + stats +
                ", participantId=" + participantId +
                ", runes=" + runes +
                ", timeline=" + timeline +
                ", teamId=" + teamId +
                ", championId=" + championId +
                ", spell1Id=" + spell1Id +
                ", spell2Id=" + spell2Id +
                ", highestAchievedSeasonTier='" + highestAchievedSeasonTier + '\'' +
                ", masteries=" + masteries +
                '}';
    }
}
