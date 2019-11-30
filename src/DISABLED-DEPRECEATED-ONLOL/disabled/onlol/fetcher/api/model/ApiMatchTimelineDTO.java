package status.disabled.onlol.fetcher.api.model;

import java.util.List;

/*
/lol/match/v4/timelines/by-match/{matchId}
 */
public class ApiMatchTimelineDTO {
    private long frameInterval = 0L;
    private List<ApiMatchFrameDTO> frames;

    public long getFrameInterval() {
        return frameInterval;
    }

    public void setFrameInterval(long frameInterval) {
        this.frameInterval = frameInterval;
    }

    public List<ApiMatchFrameDTO> getFrames() {
        return frames;
    }

    public void setFrames(List<ApiMatchFrameDTO> frames) {
        this.frames = frames;
    }

    @Override
    public String toString() {
        return "ApiMatchTimelineDto{" +
                "frameInterval=" + frameInterval +
                ", frames=" + frames +
                '}';
    }
}
