package com.onlol.fetcher.api.model;

import java.util.HashMap;
import java.util.Map;

/*
/lol/match/v4/matches/{matchId}
 */
public class ApiParticipantTimelineDTO {
    private String lane = ""; // Participant's calculated lane. MID and BOT are legacy values. (Legal values: MID, MIDDLE, TOP, JUNGLE, BOT, BOTTOM)
    private Integer participantId = 0;
    private String role = ""; // Participant's calculated role. (Legal values: DUO, NONE, SOLO, DUO_CARRY, DUO_SUPPORT)
    private Map<String, Double> csDiffPerMinDeltas = new HashMap<>(); // Creep score difference versus the calculated lane opponent(s) for a specified period.
    private Map<String, Double> goldPerMinDeltas = new HashMap<>(); // Gold for a specified period.
    private Map<String, Double> xpDiffPerMinDeltas = new HashMap<>();// Experience difference versus the calculated lane opponent(s) for a specified period.
    private Map<String, Double> creepsPerMinDeltas = new HashMap<>(); // Creeps for a specified period.
    private Map<String, Double> xpPerMinDeltas = new HashMap<>(); // Experience change for a specified period.
    private Map<String, Double> damageTakenDiffPerMinDeltas = new HashMap<>(); // Damage taken difference versus the calculated lane opponent(s) for a specified period.
    private Map<String, Double> damageTakenPerMinDeltas = new HashMap<>(); // Damage taken for a specified period.

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Map<String, Double> getCsDiffPerMinDeltas() {
        return csDiffPerMinDeltas;
    }

    public void setCsDiffPerMinDeltas(Map<String, Double> csDiffPerMinDeltas) {
        this.csDiffPerMinDeltas = csDiffPerMinDeltas;
    }

    public Map<String, Double> getGoldPerMinDeltas() {
        return goldPerMinDeltas;
    }

    public void setGoldPerMinDeltas(Map<String, Double> goldPerMinDeltas) {
        this.goldPerMinDeltas = goldPerMinDeltas;
    }

    public Map<String, Double> getXpDiffPerMinDeltas() {
        return xpDiffPerMinDeltas;
    }

    public void setXpDiffPerMinDeltas(Map<String, Double> xpDiffPerMinDeltas) {
        this.xpDiffPerMinDeltas = xpDiffPerMinDeltas;
    }

    public Map<String, Double> getCreepsPerMinDeltas() {
        return creepsPerMinDeltas;
    }

    public void setCreepsPerMinDeltas(Map<String, Double> creepsPerMinDeltas) {
        this.creepsPerMinDeltas = creepsPerMinDeltas;
    }

    public Map<String, Double> getXpPerMinDeltas() {
        return xpPerMinDeltas;
    }

    public void setXpPerMinDeltas(Map<String, Double> xpPerMinDeltas) {
        this.xpPerMinDeltas = xpPerMinDeltas;
    }

    public Map<String, Double> getDamageTakenDiffPerMinDeltas() {
        return damageTakenDiffPerMinDeltas;
    }

    public void setDamageTakenDiffPerMinDeltas(Map<String, Double> damageTakenDiffPerMinDeltas) {
        this.damageTakenDiffPerMinDeltas = damageTakenDiffPerMinDeltas;
    }

    public Map<String, Double> getDamageTakenPerMinDeltas() {
        return damageTakenPerMinDeltas;
    }

    public void setDamageTakenPerMinDeltas(Map<String, Double> damageTakenPerMinDeltas) {
        this.damageTakenPerMinDeltas = damageTakenPerMinDeltas;
    }

    @Override
    public String toString() {
        return "SampleParticipantTimeline{" +
                "lane='" + lane + '\'' +
                ", participantId=" + participantId +
                ", role='" + role + '\'' +
                ", csDiffPerMinDeltas=" + csDiffPerMinDeltas +
                ", goldPerMinDeltas=" + goldPerMinDeltas +
                ", xpDiffPerMinDeltas=" + xpDiffPerMinDeltas +
                ", creepsPerMinDeltas=" + creepsPerMinDeltas +
                ", xpPerMinDeltas=" + xpPerMinDeltas +
                ", damageTakenDiffPerMinDeltas=" + damageTakenDiffPerMinDeltas +
                ", damageTakenPerMinDeltas=" + damageTakenPerMinDeltas +
                '}';
    }
}
