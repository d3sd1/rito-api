package status.disabled.fetcher.api.model;

import java.util.ArrayList;
import java.util.List;

/*
/lol/match/v4/matchlists/by-account/{encryptedAccountId}
 */
public class ApiMatchlistDto {
    private List<ApiMatchReferenceDTO> matches = new ArrayList<>();
    private Long startIndex = 0L;
    private Long endIndex = 0L;
    private Long totalGames = 0L;

    public List<ApiMatchReferenceDTO> getMatches() {
        return matches;
    }

    public void setMatches(List<ApiMatchReferenceDTO> matches) {
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
