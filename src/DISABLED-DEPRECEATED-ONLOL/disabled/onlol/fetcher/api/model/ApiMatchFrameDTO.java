package status.disabled.onlol.fetcher.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
/lol/match/v4/timelines/by-match/{matchId}
 */
public class ApiMatchFrameDTO {
    private Long timestamp = 0L;
    private Map<String, ApiMatchParticipantFrameDTO> participantFrames = new HashMap<>();
    private List<ApiMatchEventDTO> events = new ArrayList<>();

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, ApiMatchParticipantFrameDTO> getParticipantFrames() {
        return participantFrames;
    }

    public void setParticipantFrames(Map<String, ApiMatchParticipantFrameDTO> participantFrames) {
        this.participantFrames = participantFrames;
    }

    public List<ApiMatchEventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<ApiMatchEventDTO> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "ApiMatchFrameDto{" +
                "timestamp=" + timestamp +
                ", participantFrames=" + participantFrames +
                ", events=" + events +
                '}';
    }
}
