package com.onlol.fetcher.api.model;

import java.util.ArrayList;
import java.util.List;

/*
/lol/match/v4/timelines/by-match/{matchId}
 */
public class ApiMatchEventDTO {
    private String eventType = "";
    private String towerType = "";
    private Integer teamId = 0;
    private String ascendedType = "";
    private Integer killerId = 0;
    private String levelUpType = "";
    private String pointCaptured = "";
    private List<Integer> assistingParticipantIds = new ArrayList<>();
    private String wardType = "";
    private String monsterType = "";
    private String type = ""; //	(Legal values: CHAMPION_KILL, WARD_PLACED, WARD_KILL, BUILDING_KILL, ELITE_MONSTER_KILL, ITEM_PURCHASED, ITEM_SOLD, ITEM_DESTROYED, ITEM_UNDO, SKILL_LEVEL_UP, ASCENDED_EVENT, CAPTURE_POINT, PORO_KING_SUMMON)
    private Integer skillSlot = 0;
    private Integer victimId = 0;
    private Long timestamp = 0L;
    private Integer afterId = 0;
    private String monsterSubType = "";
    private String laneType = "";
    private Integer itemId = 0;
    private Integer participantId = 0;
    private String buildingType = "";
    private Integer creatorId = 0;
    private ApiMatchPositionDTO position = new ApiMatchPositionDTO();
    private Integer beforeId = 0;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getTowerType() {
        return towerType;
    }

    public void setTowerType(String towerType) {
        this.towerType = towerType;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getAscendedType() {
        return ascendedType;
    }

    public void setAscendedType(String ascendedType) {
        this.ascendedType = ascendedType;
    }

    public Integer getKillerId() {
        return killerId;
    }

    public void setKillerId(Integer killerId) {
        this.killerId = killerId;
    }

    public String getLevelUpType() {
        return levelUpType;
    }

    public void setLevelUpType(String levelUpType) {
        this.levelUpType = levelUpType;
    }

    public String getPointCaptured() {
        return pointCaptured;
    }

    public void setPointCaptured(String pointCaptured) {
        this.pointCaptured = pointCaptured;
    }

    public List<Integer> getAssistingParticipantIds() {
        return assistingParticipantIds;
    }

    public void setAssistingParticipantIds(List<Integer> assistingParticipantIds) {
        this.assistingParticipantIds = assistingParticipantIds;
    }

    public String getWardType() {
        return wardType;
    }

    public void setWardType(String wardType) {
        this.wardType = wardType;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(String monsterType) {
        this.monsterType = monsterType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSkillSlot() {
        return skillSlot;
    }

    public void setSkillSlot(Integer skillSlot) {
        this.skillSlot = skillSlot;
    }

    public Integer getVictimId() {
        return victimId;
    }

    public void setVictimId(Integer victimId) {
        this.victimId = victimId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getAfterId() {
        return afterId;
    }

    public void setAfterId(Integer afterId) {
        this.afterId = afterId;
    }

    public String getMonsterSubType() {
        return monsterSubType;
    }

    public void setMonsterSubType(String monsterSubType) {
        this.monsterSubType = monsterSubType;
    }

    public String getLaneType() {
        return laneType;
    }

    public void setLaneType(String laneType) {
        this.laneType = laneType;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public ApiMatchPositionDTO getPosition() {
        return position;
    }

    public void setPosition(ApiMatchPositionDTO position) {
        this.position = position;
    }

    public Integer getBeforeId() {
        return beforeId;
    }

    public void setBeforeId(Integer beforeId) {
        this.beforeId = beforeId;
    }

    @Override
    public String toString() {
        return "ApiMatchEventDto{" +
                "eventType='" + eventType + '\'' +
                ", towerType='" + towerType + '\'' +
                ", teamId=" + teamId +
                ", ascendedType='" + ascendedType + '\'' +
                ", killerId=" + killerId +
                ", levelUpType='" + levelUpType + '\'' +
                ", pointCaptured='" + pointCaptured + '\'' +
                ", assistingParticipantIds=" + assistingParticipantIds +
                ", wardType='" + wardType + '\'' +
                ", monsterType='" + monsterType + '\'' +
                ", type='" + type + '\'' +
                ", skillSlot=" + skillSlot +
                ", victimId=" + victimId +
                ", timestamp=" + timestamp +
                ", afterId=" + afterId +
                ", monsterSubType='" + monsterSubType + '\'' +
                ", laneType='" + laneType + '\'' +
                ", itemId=" + itemId +
                ", participantId=" + participantId +
                ", buildingType='" + buildingType + '\'' +
                ", creatorId=" + creatorId +
                ", position=" + position +
                ", beforeId=" + beforeId +
                '}';
    }
}
