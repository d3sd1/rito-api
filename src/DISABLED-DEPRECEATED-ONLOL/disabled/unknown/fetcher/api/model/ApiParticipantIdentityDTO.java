package status.disabled.unknown.fetcher.api.model;

/*
/lol/match/v4/matches/{matchId}
 */
public class ApiParticipantIdentityDTO {
    private Integer participantId = 0;
    private ApiPlayerDTO player = new ApiPlayerDTO(); // Player information.


    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public ApiPlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(ApiPlayerDTO player) {
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
