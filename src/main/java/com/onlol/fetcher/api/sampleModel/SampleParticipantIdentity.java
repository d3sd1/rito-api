package com.onlol.fetcher.api.sampleModel;

public class SampleParticipantIdentity {
    private Integer participantId;
    private SamplePlayer player;

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public SamplePlayer getPlayer() {
        return player;
    }

    public void setPlayer(SamplePlayer player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "SampleParticipantIdentity{" +
                "participantId=" + participantId +
                ", player=" + player +
                '}';
    }
}
