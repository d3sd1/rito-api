package com.onlol.fetcher.api.model;

/*
/lol/summoner/v4/summoners/by-account/{encryptedAccountId}
/lol/summoner/v4/summoners/by-name/{summonerName}
/lol/summoner/v4/summoners/by-puuid/{encryptedPUUID}
/lol/summoner/v4/summoners/{encryptedSummonerId}
 */
public class ApiSummonerDTO {
    private Integer profileIconId = 0; // ID of the summoner icon associated with the summoner.
    private String name = ""; //	Summoner name.
    private String puuid = ""; // Encrypted PUUID. Exact length of 78 characters.
    private Long summonerLevel = 0L; // Summoner level associated with the summoner.
    private Long revisionDate = 0L; // Date summoner was last modified specified as epoch milliseconds. The following events will update this timestamp: profile icon change, playing the tutorial or advanced tutorial, finishing a game, summoner name change
    private String id = ""; // Encrypted summoner ID. Max length 63 characters.
    private String accountId = ""; //	Encrypted account ID. Max length 56 characters.

    public Integer getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(Integer profileIconId) {
        this.profileIconId = profileIconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public Long getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(Long summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public Long getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Long revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "ApiSummonerDTO{" +
                "profileIconId=" + profileIconId +
                ", name='" + name + '\'' +
                ", puuid='" + puuid + '\'' +
                ", summonerLevel=" + summonerLevel +
                ", revisionDate=" + revisionDate +
                ", id='" + id + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
