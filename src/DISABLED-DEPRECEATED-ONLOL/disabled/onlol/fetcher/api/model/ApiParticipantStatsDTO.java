package status.disabled.onlol.fetcher.api.model;

/*
/lol/match/v4/matches/{matchId}
 */
public class ApiParticipantStatsDTO {
    private boolean firstBloodAssist = false;
    private Long visionScore = 0L;
    private Long magicDamageDealtToChampions = 0L;
    private Long damageDealtToObjectives = 0L;
    private Integer totalTimeCrowdControlDealt = 0;
    private Integer longestTimeSpentLiving = 0;
    private Integer perk0Var1 = 0; // Post game rune stats.
    private Integer perk0Var2 = 0; // Post game rune stats.
    private Integer perk0Var3 = 0; // Post game rune stats.
    private Integer perk1Var1 = 0; // Post game rune stats.
    private Integer perk1Var2 = 0; // Post game rune stats.
    private Integer perk1Var3 = 0; // Post game rune stats.
    private Integer perk2Var1 = 0; // Post game rune stats.
    private Integer perk2Var2 = 0; // Post game rune stats.
    private Integer perk2Var3 = 0; // Post game rune stats.
    private Integer perk3Var1 = 0; // Post game rune stats.
    private Integer perk3Var2 = 0; // Post game rune stats.
    private Integer perk3Var3 = 0; // Post game rune stats.
    private Integer perk4Var1 = 0; // Post game rune stats.
    private Integer perk4Var2 = 0; // Post game rune stats.
    private Integer perk4Var3 = 0; // Post game rune stats.
    private Integer perk5Var1 = 0; // Post game rune stats.
    private Integer perk5Var2 = 0; // Post game rune stats.
    private Integer perk5Var3 = 0; // Post game rune stats.
    private Integer playerScore0 = 0;
    private Integer playerScore1 = 0;
    private Integer playerScore2 = 0;
    private Integer playerScore3 = 0;
    private Integer playerScore4 = 0;
    private Integer playerScore5 = 0;
    private Integer playerScore6 = 0;
    private Integer playerScore7 = 0;
    private Integer playerScore8 = 0;
    private Integer playerScore9 = 0;
    private Integer totalScoreRank = 0;

    private Integer nodeNeutralizeAssist = 0;
    private Long damageDealtToTurrets = 0L;
    private Long physicalDamageDealtToChampions = 0L;
    private Integer nodeCapture = 0;
    private Integer totalUnitsHealed = 0;
    private Integer wardsKilled = 0;
    private Integer largestCriticalStrike = 0;
    private Integer largestKillingSpree = 0;
    private Integer teamObjective = 0;
    private Long magicDamageDealt = 0L;
    private Long damageSelfMitigated = 0L;
    private Long magicalDamageTaken = 0L;
    private Long trueDamageTaken = 0L;
    private boolean firstInhibitorKill = false;
    private Integer nodeNeutralize = 0;
    private Integer combatPlayerScore = 0;
    private Long trueDamageDealt = 0L;
    private Long totalDamageTaken = 0L;
    private Long physicalDamageDealt = 0L;
    private Integer sightWardsBoughtInGame = 0;
    private Long totalDamageDealtToChampions = 0L;
    private Long physicalDamageTaken = 0L;
    private Integer totalPlayerScore = 0;
    private Integer objectivePlayerScore = 0;
    private Long totalDamageDealt = 0L;
    private Integer wardsPlaced = 0;
    private Integer turretKills = 0;
    private Long trueDamageDealtToChampions = 0L;
    private boolean firstBloodKill = false;
    private boolean firstTowerAssist = false;
    private boolean firstTowerKill = false;
    private boolean firstInhibitorAssist = false;
    private Integer visionWardsBoughtInGame = 0;
    private Long totalHeal = 0L;
    private Long timeCCingOthers = 0L;

    private Integer neutralMinionsKilled = 0;
    private Integer neutralMinionsKilledTeamJungle = 0;
    private Integer neutralMinionsKilledEnemyJungle = 0;
    private Integer totalMinionsKilled = 0;
    private Integer largestMultiKill = 0;
    private Integer killingSprees = 0;
    private Integer kills = 0;
    private Integer deaths = 0;
    private Integer assists = 0;
    private Integer doubleKills = 0;
    private Integer tripleKills = 0;
    private Integer quadraKills = 0;
    private Integer pentaKills = 0;
    private Integer unrealKills = 0;
    private Integer inhibitorKills = 0;


    private Integer goldSpent = 0;

    private Integer item0 = 0;
    private Integer item1 = 0;
    private Integer item2 = 0;
    private Integer item3 = 0;
    private Integer item4 = 0;
    private Integer item5 = 0;
    private Integer item6 = 0;

    private Integer perkPrimaryStyle = 0; // Primary rune path
    private Integer perkSubStyle = 0; // Secondary rune path
    private Integer perk0 = 0; // Primary path keystone rune.
    private Integer perk1 = 0; // Primary path keystone rune.
    private Integer perk2 = 0; // Primary path keystone rune.
    private Integer perk3 = 0; // Primary path keystone rune.
    private Integer perk4 = 0; // Secondary path keystone rune.
    private Integer perk5 = 0; // Secondary path keystone rune.


    private Integer participantId = 0;
    private boolean win = false;
    private Integer champLevel = 0;
    private Integer goldEarned = 0;


    private Integer altarsCaptured = 0;
    private Integer nodeCaptureAssist = 0;
    private Integer altarsNeutralized = 0;

    public boolean isFirstBloodAssist() {
        return firstBloodAssist;
    }

    public void setFirstBloodAssist(boolean firstBloodAssist) {
        this.firstBloodAssist = firstBloodAssist;
    }

    public Long getVisionScore() {
        return visionScore;
    }

    public void setVisionScore(Long visionScore) {
        this.visionScore = visionScore;
    }

    public Long getMagicDamageDealtToChampions() {
        return magicDamageDealtToChampions;
    }

    public void setMagicDamageDealtToChampions(Long magicDamageDealtToChampions) {
        this.magicDamageDealtToChampions = magicDamageDealtToChampions;
    }

    public Long getDamageDealtToObjectives() {
        return damageDealtToObjectives;
    }

    public void setDamageDealtToObjectives(Long damageDealtToObjectives) {
        this.damageDealtToObjectives = damageDealtToObjectives;
    }

    public Integer getTotalTimeCrowdControlDealt() {
        return totalTimeCrowdControlDealt;
    }

    public void setTotalTimeCrowdControlDealt(Integer totalTimeCrowdControlDealt) {
        this.totalTimeCrowdControlDealt = totalTimeCrowdControlDealt;
    }

    public Integer getLongestTimeSpentLiving() {
        return longestTimeSpentLiving;
    }

    public void setLongestTimeSpentLiving(Integer longestTimeSpentLiving) {
        this.longestTimeSpentLiving = longestTimeSpentLiving;
    }

    public Integer getPerk0Var1() {
        return perk0Var1;
    }

    public void setPerk0Var1(Integer perk0Var1) {
        this.perk0Var1 = perk0Var1;
    }

    public Integer getPerk0Var2() {
        return perk0Var2;
    }

    public void setPerk0Var2(Integer perk0Var2) {
        this.perk0Var2 = perk0Var2;
    }

    public Integer getPerk0Var3() {
        return perk0Var3;
    }

    public void setPerk0Var3(Integer perk0Var3) {
        this.perk0Var3 = perk0Var3;
    }

    public Integer getPerk1Var1() {
        return perk1Var1;
    }

    public void setPerk1Var1(Integer perk1Var1) {
        this.perk1Var1 = perk1Var1;
    }

    public Integer getPerk1Var2() {
        return perk1Var2;
    }

    public void setPerk1Var2(Integer perk1Var2) {
        this.perk1Var2 = perk1Var2;
    }

    public Integer getPerk1Var3() {
        return perk1Var3;
    }

    public void setPerk1Var3(Integer perk1Var3) {
        this.perk1Var3 = perk1Var3;
    }

    public Integer getPerk2Var1() {
        return perk2Var1;
    }

    public void setPerk2Var1(Integer perk2Var1) {
        this.perk2Var1 = perk2Var1;
    }

    public Integer getPerk2Var2() {
        return perk2Var2;
    }

    public void setPerk2Var2(Integer perk2Var2) {
        this.perk2Var2 = perk2Var2;
    }

    public Integer getPerk2Var3() {
        return perk2Var3;
    }

    public void setPerk2Var3(Integer perk2Var3) {
        this.perk2Var3 = perk2Var3;
    }

    public Integer getPerk3Var1() {
        return perk3Var1;
    }

    public void setPerk3Var1(Integer perk3Var1) {
        this.perk3Var1 = perk3Var1;
    }

    public Integer getPerk3Var2() {
        return perk3Var2;
    }

    public void setPerk3Var2(Integer perk3Var2) {
        this.perk3Var2 = perk3Var2;
    }

    public Integer getPerk3Var3() {
        return perk3Var3;
    }

    public void setPerk3Var3(Integer perk3Var3) {
        this.perk3Var3 = perk3Var3;
    }

    public Integer getPerk4Var1() {
        return perk4Var1;
    }

    public void setPerk4Var1(Integer perk4Var1) {
        this.perk4Var1 = perk4Var1;
    }

    public Integer getPerk4Var2() {
        return perk4Var2;
    }

    public void setPerk4Var2(Integer perk4Var2) {
        this.perk4Var2 = perk4Var2;
    }

    public Integer getPerk4Var3() {
        return perk4Var3;
    }

    public void setPerk4Var3(Integer perk4Var3) {
        this.perk4Var3 = perk4Var3;
    }

    public Integer getPerk5Var1() {
        return perk5Var1;
    }

    public void setPerk5Var1(Integer perk5Var1) {
        this.perk5Var1 = perk5Var1;
    }

    public Integer getPerk5Var2() {
        return perk5Var2;
    }

    public void setPerk5Var2(Integer perk5Var2) {
        this.perk5Var2 = perk5Var2;
    }

    public Integer getPerk5Var3() {
        return perk5Var3;
    }

    public void setPerk5Var3(Integer perk5Var3) {
        this.perk5Var3 = perk5Var3;
    }

    public Integer getPlayerScore0() {
        return playerScore0;
    }

    public void setPlayerScore0(Integer playerScore0) {
        this.playerScore0 = playerScore0;
    }

    public Integer getPlayerScore1() {
        return playerScore1;
    }

    public void setPlayerScore1(Integer playerScore1) {
        this.playerScore1 = playerScore1;
    }

    public Integer getPlayerScore2() {
        return playerScore2;
    }

    public void setPlayerScore2(Integer playerScore2) {
        this.playerScore2 = playerScore2;
    }

    public Integer getPlayerScore3() {
        return playerScore3;
    }

    public void setPlayerScore3(Integer playerScore3) {
        this.playerScore3 = playerScore3;
    }

    public Integer getPlayerScore4() {
        return playerScore4;
    }

    public void setPlayerScore4(Integer playerScore4) {
        this.playerScore4 = playerScore4;
    }

    public Integer getPlayerScore5() {
        return playerScore5;
    }

    public void setPlayerScore5(Integer playerScore5) {
        this.playerScore5 = playerScore5;
    }

    public Integer getPlayerScore6() {
        return playerScore6;
    }

    public void setPlayerScore6(Integer playerScore6) {
        this.playerScore6 = playerScore6;
    }

    public Integer getPlayerScore7() {
        return playerScore7;
    }

    public void setPlayerScore7(Integer playerScore7) {
        this.playerScore7 = playerScore7;
    }

    public Integer getPlayerScore8() {
        return playerScore8;
    }

    public void setPlayerScore8(Integer playerScore8) {
        this.playerScore8 = playerScore8;
    }

    public Integer getPlayerScore9() {
        return playerScore9;
    }

    public void setPlayerScore9(Integer playerScore9) {
        this.playerScore9 = playerScore9;
    }

    public Integer getTotalScoreRank() {
        return totalScoreRank;
    }

    public void setTotalScoreRank(Integer totalScoreRank) {
        this.totalScoreRank = totalScoreRank;
    }

    public Integer getNodeNeutralizeAssist() {
        return nodeNeutralizeAssist;
    }

    public void setNodeNeutralizeAssist(Integer nodeNeutralizeAssist) {
        this.nodeNeutralizeAssist = nodeNeutralizeAssist;
    }

    public Long getDamageDealtToTurrets() {
        return damageDealtToTurrets;
    }

    public void setDamageDealtToTurrets(Long damageDealtToTurrets) {
        this.damageDealtToTurrets = damageDealtToTurrets;
    }

    public Long getPhysicalDamageDealtToChampions() {
        return physicalDamageDealtToChampions;
    }

    public void setPhysicalDamageDealtToChampions(Long physicalDamageDealtToChampions) {
        this.physicalDamageDealtToChampions = physicalDamageDealtToChampions;
    }

    public Integer getNodeCapture() {
        return nodeCapture;
    }

    public void setNodeCapture(Integer nodeCapture) {
        this.nodeCapture = nodeCapture;
    }

    public Integer getTotalUnitsHealed() {
        return totalUnitsHealed;
    }

    public void setTotalUnitsHealed(Integer totalUnitsHealed) {
        this.totalUnitsHealed = totalUnitsHealed;
    }

    public Integer getWardsKilled() {
        return wardsKilled;
    }

    public void setWardsKilled(Integer wardsKilled) {
        this.wardsKilled = wardsKilled;
    }

    public Integer getLargestCriticalStrike() {
        return largestCriticalStrike;
    }

    public void setLargestCriticalStrike(Integer largestCriticalStrike) {
        this.largestCriticalStrike = largestCriticalStrike;
    }

    public Integer getLargestKillingSpree() {
        return largestKillingSpree;
    }

    public void setLargestKillingSpree(Integer largestKillingSpree) {
        this.largestKillingSpree = largestKillingSpree;
    }

    public Integer getTeamObjective() {
        return teamObjective;
    }

    public void setTeamObjective(Integer teamObjective) {
        this.teamObjective = teamObjective;
    }

    public Long getMagicDamageDealt() {
        return magicDamageDealt;
    }

    public void setMagicDamageDealt(Long magicDamageDealt) {
        this.magicDamageDealt = magicDamageDealt;
    }

    public Long getDamageSelfMitigated() {
        return damageSelfMitigated;
    }

    public void setDamageSelfMitigated(Long damageSelfMitigated) {
        this.damageSelfMitigated = damageSelfMitigated;
    }

    public Long getMagicalDamageTaken() {
        return magicalDamageTaken;
    }

    public void setMagicalDamageTaken(Long magicalDamageTaken) {
        this.magicalDamageTaken = magicalDamageTaken;
    }

    public Long getTrueDamageTaken() {
        return trueDamageTaken;
    }

    public void setTrueDamageTaken(Long trueDamageTaken) {
        this.trueDamageTaken = trueDamageTaken;
    }

    public boolean isFirstInhibitorKill() {
        return firstInhibitorKill;
    }

    public void setFirstInhibitorKill(boolean firstInhibitorKill) {
        this.firstInhibitorKill = firstInhibitorKill;
    }

    public Integer getNodeNeutralize() {
        return nodeNeutralize;
    }

    public void setNodeNeutralize(Integer nodeNeutralize) {
        this.nodeNeutralize = nodeNeutralize;
    }

    public Integer getCombatPlayerScore() {
        return combatPlayerScore;
    }

    public void setCombatPlayerScore(Integer combatPlayerScore) {
        this.combatPlayerScore = combatPlayerScore;
    }

    public Long getTrueDamageDealt() {
        return trueDamageDealt;
    }

    public void setTrueDamageDealt(Long trueDamageDealt) {
        this.trueDamageDealt = trueDamageDealt;
    }

    public Long getTotalDamageTaken() {
        return totalDamageTaken;
    }

    public void setTotalDamageTaken(Long totalDamageTaken) {
        this.totalDamageTaken = totalDamageTaken;
    }

    public Long getPhysicalDamageDealt() {
        return physicalDamageDealt;
    }

    public void setPhysicalDamageDealt(Long physicalDamageDealt) {
        this.physicalDamageDealt = physicalDamageDealt;
    }

    public Integer getSightWardsBoughtInGame() {
        return sightWardsBoughtInGame;
    }

    public void setSightWardsBoughtInGame(Integer sightWardsBoughtInGame) {
        this.sightWardsBoughtInGame = sightWardsBoughtInGame;
    }

    public Long getTotalDamageDealtToChampions() {
        return totalDamageDealtToChampions;
    }

    public void setTotalDamageDealtToChampions(Long totalDamageDealtToChampions) {
        this.totalDamageDealtToChampions = totalDamageDealtToChampions;
    }

    public Long getPhysicalDamageTaken() {
        return physicalDamageTaken;
    }

    public void setPhysicalDamageTaken(Long physicalDamageTaken) {
        this.physicalDamageTaken = physicalDamageTaken;
    }

    public Integer getTotalPlayerScore() {
        return totalPlayerScore;
    }

    public void setTotalPlayerScore(Integer totalPlayerScore) {
        this.totalPlayerScore = totalPlayerScore;
    }

    public Integer getObjectivePlayerScore() {
        return objectivePlayerScore;
    }

    public void setObjectivePlayerScore(Integer objectivePlayerScore) {
        this.objectivePlayerScore = objectivePlayerScore;
    }

    public Long getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public void setTotalDamageDealt(Long totalDamageDealt) {
        this.totalDamageDealt = totalDamageDealt;
    }

    public Integer getWardsPlaced() {
        return wardsPlaced;
    }

    public void setWardsPlaced(Integer wardsPlaced) {
        this.wardsPlaced = wardsPlaced;
    }

    public Integer getTurretKills() {
        return turretKills;
    }

    public void setTurretKills(Integer turretKills) {
        this.turretKills = turretKills;
    }

    public Long getTrueDamageDealtToChampions() {
        return trueDamageDealtToChampions;
    }

    public void setTrueDamageDealtToChampions(Long trueDamageDealtToChampions) {
        this.trueDamageDealtToChampions = trueDamageDealtToChampions;
    }

    public boolean isFirstBloodKill() {
        return firstBloodKill;
    }

    public void setFirstBloodKill(boolean firstBloodKill) {
        this.firstBloodKill = firstBloodKill;
    }

    public boolean isFirstTowerAssist() {
        return firstTowerAssist;
    }

    public void setFirstTowerAssist(boolean firstTowerAssist) {
        this.firstTowerAssist = firstTowerAssist;
    }

    public boolean isFirstTowerKill() {
        return firstTowerKill;
    }

    public void setFirstTowerKill(boolean firstTowerKill) {
        this.firstTowerKill = firstTowerKill;
    }

    public boolean isFirstInhibitorAssist() {
        return firstInhibitorAssist;
    }

    public void setFirstInhibitorAssist(boolean firstInhibitorAssist) {
        this.firstInhibitorAssist = firstInhibitorAssist;
    }

    public Integer getVisionWardsBoughtInGame() {
        return visionWardsBoughtInGame;
    }

    public void setVisionWardsBoughtInGame(Integer visionWardsBoughtInGame) {
        this.visionWardsBoughtInGame = visionWardsBoughtInGame;
    }

    public Long getTotalHeal() {
        return totalHeal;
    }

    public void setTotalHeal(Long totalHeal) {
        this.totalHeal = totalHeal;
    }

    public Long getTimeCCingOthers() {
        return timeCCingOthers;
    }

    public void setTimeCCingOthers(Long timeCCingOthers) {
        this.timeCCingOthers = timeCCingOthers;
    }

    public Integer getNeutralMinionsKilled() {
        return neutralMinionsKilled;
    }

    public void setNeutralMinionsKilled(Integer neutralMinionsKilled) {
        this.neutralMinionsKilled = neutralMinionsKilled;
    }

    public Integer getNeutralMinionsKilledTeamJungle() {
        return neutralMinionsKilledTeamJungle;
    }

    public void setNeutralMinionsKilledTeamJungle(Integer neutralMinionsKilledTeamJungle) {
        this.neutralMinionsKilledTeamJungle = neutralMinionsKilledTeamJungle;
    }

    public Integer getNeutralMinionsKilledEnemyJungle() {
        return neutralMinionsKilledEnemyJungle;
    }

    public void setNeutralMinionsKilledEnemyJungle(Integer neutralMinionsKilledEnemyJungle) {
        this.neutralMinionsKilledEnemyJungle = neutralMinionsKilledEnemyJungle;
    }

    public Integer getTotalMinionsKilled() {
        return totalMinionsKilled;
    }

    public void setTotalMinionsKilled(Integer totalMinionsKilled) {
        this.totalMinionsKilled = totalMinionsKilled;
    }

    public Integer getLargestMultiKill() {
        return largestMultiKill;
    }

    public void setLargestMultiKill(Integer largestMultiKill) {
        this.largestMultiKill = largestMultiKill;
    }

    public Integer getKillingSprees() {
        return killingSprees;
    }

    public void setKillingSprees(Integer killingSprees) {
        this.killingSprees = killingSprees;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getDoubleKills() {
        return doubleKills;
    }

    public void setDoubleKills(Integer doubleKills) {
        this.doubleKills = doubleKills;
    }

    public Integer getTripleKills() {
        return tripleKills;
    }

    public void setTripleKills(Integer tripleKills) {
        this.tripleKills = tripleKills;
    }

    public Integer getQuadraKills() {
        return quadraKills;
    }

    public void setQuadraKills(Integer quadraKills) {
        this.quadraKills = quadraKills;
    }

    public Integer getPentaKills() {
        return pentaKills;
    }

    public void setPentaKills(Integer pentaKills) {
        this.pentaKills = pentaKills;
    }

    public Integer getUnrealKills() {
        return unrealKills;
    }

    public void setUnrealKills(Integer unrealKills) {
        this.unrealKills = unrealKills;
    }

    public Integer getInhibitorKills() {
        return inhibitorKills;
    }

    public void setInhibitorKills(Integer inhibitorKills) {
        this.inhibitorKills = inhibitorKills;
    }

    public Integer getGoldSpent() {
        return goldSpent;
    }

    public void setGoldSpent(Integer goldSpent) {
        this.goldSpent = goldSpent;
    }

    public Integer getItem0() {
        return item0;
    }

    public void setItem0(Integer item0) {
        this.item0 = item0;
    }

    public Integer getItem1() {
        return item1;
    }

    public void setItem1(Integer item1) {
        this.item1 = item1;
    }

    public Integer getItem2() {
        return item2;
    }

    public void setItem2(Integer item2) {
        this.item2 = item2;
    }

    public Integer getItem3() {
        return item3;
    }

    public void setItem3(Integer item3) {
        this.item3 = item3;
    }

    public Integer getItem4() {
        return item4;
    }

    public void setItem4(Integer item4) {
        this.item4 = item4;
    }

    public Integer getItem5() {
        return item5;
    }

    public void setItem5(Integer item5) {
        this.item5 = item5;
    }

    public Integer getItem6() {
        return item6;
    }

    public void setItem6(Integer item6) {
        this.item6 = item6;
    }

    public Integer getPerkPrimaryStyle() {
        return perkPrimaryStyle;
    }

    public void setPerkPrimaryStyle(Integer perkPrimaryStyle) {
        this.perkPrimaryStyle = perkPrimaryStyle;
    }

    public Integer getPerkSubStyle() {
        return perkSubStyle;
    }

    public void setPerkSubStyle(Integer perkSubStyle) {
        this.perkSubStyle = perkSubStyle;
    }

    public Integer getPerk0() {
        return perk0;
    }

    public void setPerk0(Integer perk0) {
        this.perk0 = perk0;
    }

    public Integer getPerk1() {
        return perk1;
    }

    public void setPerk1(Integer perk1) {
        this.perk1 = perk1;
    }

    public Integer getPerk2() {
        return perk2;
    }

    public void setPerk2(Integer perk2) {
        this.perk2 = perk2;
    }

    public Integer getPerk3() {
        return perk3;
    }

    public void setPerk3(Integer perk3) {
        this.perk3 = perk3;
    }

    public Integer getPerk4() {
        return perk4;
    }

    public void setPerk4(Integer perk4) {
        this.perk4 = perk4;
    }

    public Integer getPerk5() {
        return perk5;
    }

    public void setPerk5(Integer perk5) {
        this.perk5 = perk5;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public Integer getChampLevel() {
        return champLevel;
    }

    public void setChampLevel(Integer champLevel) {
        this.champLevel = champLevel;
    }

    public Integer getGoldEarned() {
        return goldEarned;
    }

    public void setGoldEarned(Integer goldEarned) {
        this.goldEarned = goldEarned;
    }

    public Integer getAltarsCaptured() {
        return altarsCaptured;
    }

    public void setAltarsCaptured(Integer altarsCaptured) {
        this.altarsCaptured = altarsCaptured;
    }

    public Integer getNodeCaptureAssist() {
        return nodeCaptureAssist;
    }

    public void setNodeCaptureAssist(Integer nodeCaptureAssist) {
        this.nodeCaptureAssist = nodeCaptureAssist;
    }

    public Integer getAltarsNeutralized() {
        return altarsNeutralized;
    }

    public void setAltarsNeutralized(Integer altarsNeutralized) {
        this.altarsNeutralized = altarsNeutralized;
    }

    @Override
    public String toString() {
        return "SampleParticipantStats{" +
                "firstBloodAssist=" + firstBloodAssist +
                ", visionScore=" + visionScore +
                ", magicDamageDealtToChampions=" + magicDamageDealtToChampions +
                ", damageDealtToObjectives=" + damageDealtToObjectives +
                ", totalTimeCrowdControlDealt=" + totalTimeCrowdControlDealt +
                ", longestTimeSpentLiving=" + longestTimeSpentLiving +
                ", perk0Var1=" + perk0Var1 +
                ", perk0Var2=" + perk0Var2 +
                ", perk0Var3=" + perk0Var3 +
                ", perk1Var1=" + perk1Var1 +
                ", perk1Var2=" + perk1Var2 +
                ", perk1Var3=" + perk1Var3 +
                ", perk2Var1=" + perk2Var1 +
                ", perk2Var2=" + perk2Var2 +
                ", perk2Var3=" + perk2Var3 +
                ", perk3Var1=" + perk3Var1 +
                ", perk3Var2=" + perk3Var2 +
                ", perk3Var3=" + perk3Var3 +
                ", perk4Var1=" + perk4Var1 +
                ", perk4Var2=" + perk4Var2 +
                ", perk4Var3=" + perk4Var3 +
                ", perk5Var1=" + perk5Var1 +
                ", perk5Var2=" + perk5Var2 +
                ", perk5Var3=" + perk5Var3 +
                ", playerScore0=" + playerScore0 +
                ", playerScore1=" + playerScore1 +
                ", playerScore2=" + playerScore2 +
                ", playerScore3=" + playerScore3 +
                ", playerScore4=" + playerScore4 +
                ", playerScore5=" + playerScore5 +
                ", playerScore6=" + playerScore6 +
                ", playerScore7=" + playerScore7 +
                ", playerScore8=" + playerScore8 +
                ", playerScore9=" + playerScore9 +
                ", totalScoreRank=" + totalScoreRank +
                ", nodeNeutralizeAssist=" + nodeNeutralizeAssist +
                ", damageDealtToTurrets=" + damageDealtToTurrets +
                ", physicalDamageDealtToChampions=" + physicalDamageDealtToChampions +
                ", nodeCapture=" + nodeCapture +
                ", totalUnitsHealed=" + totalUnitsHealed +
                ", wardsKilled=" + wardsKilled +
                ", largestCriticalStrike=" + largestCriticalStrike +
                ", largestKillingSpree=" + largestKillingSpree +
                ", teamObjective=" + teamObjective +
                ", magicDamageDealt=" + magicDamageDealt +
                ", damageSelfMitigated=" + damageSelfMitigated +
                ", magicalDamageTaken=" + magicalDamageTaken +
                ", trueDamageTaken=" + trueDamageTaken +
                ", firstInhibitorKill=" + firstInhibitorKill +
                ", nodeNeutralize=" + nodeNeutralize +
                ", combatPlayerScore=" + combatPlayerScore +
                ", trueDamageDealt=" + trueDamageDealt +
                ", totalDamageTaken=" + totalDamageTaken +
                ", physicalDamageDealt=" + physicalDamageDealt +
                ", sightWardsBoughtInGame=" + sightWardsBoughtInGame +
                ", totalDamageDealtToChampions=" + totalDamageDealtToChampions +
                ", physicalDamageTaken=" + physicalDamageTaken +
                ", totalPlayerScore=" + totalPlayerScore +
                ", objectivePlayerScore=" + objectivePlayerScore +
                ", totalDamageDealt=" + totalDamageDealt +
                ", wardsPlaced=" + wardsPlaced +
                ", turretKills=" + turretKills +
                ", trueDamageDealtToChampions=" + trueDamageDealtToChampions +
                ", firstBloodKill=" + firstBloodKill +
                ", firstTowerAssist=" + firstTowerAssist +
                ", firstTowerKill=" + firstTowerKill +
                ", firstInhibitorAssist=" + firstInhibitorAssist +
                ", visionWardsBoughtInGame=" + visionWardsBoughtInGame +
                ", totalHeal=" + totalHeal +
                ", timeCCingOthers=" + timeCCingOthers +
                ", neutralMinionsKilled=" + neutralMinionsKilled +
                ", neutralMinionsKilledTeamJungle=" + neutralMinionsKilledTeamJungle +
                ", neutralMinionsKilledEnemyJungle=" + neutralMinionsKilledEnemyJungle +
                ", totalMinionsKilled=" + totalMinionsKilled +
                ", largestMultiKill=" + largestMultiKill +
                ", killingSprees=" + killingSprees +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", assists=" + assists +
                ", doubleKills=" + doubleKills +
                ", tripleKills=" + tripleKills +
                ", quadraKills=" + quadraKills +
                ", pentaKills=" + pentaKills +
                ", unrealKills=" + unrealKills +
                ", inhibitorKills=" + inhibitorKills +
                ", goldSpent=" + goldSpent +
                ", item0=" + item0 +
                ", item1=" + item1 +
                ", item2=" + item2 +
                ", item3=" + item3 +
                ", item4=" + item4 +
                ", item5=" + item5 +
                ", item6=" + item6 +
                ", perkPrimaryStyle=" + perkPrimaryStyle +
                ", perkSubStyle=" + perkSubStyle +
                ", perk0=" + perk0 +
                ", perk1=" + perk1 +
                ", perk2=" + perk2 +
                ", perk3=" + perk3 +
                ", perk4=" + perk4 +
                ", perk5=" + perk5 +
                ", participantId=" + participantId +
                ", win=" + win +
                ", champLevel=" + champLevel +
                ", goldEarned=" + goldEarned +
                ", altarsCaptured=" + altarsCaptured +
                ", nodeCaptureAssist=" + nodeCaptureAssist +
                ", altarsNeutralized=" + altarsNeutralized +
                '}';
    }
}
