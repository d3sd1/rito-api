package com.onlol.fetcher.api.endpoints;

public class V4 {
    public static final String SUMMONERS_BY_NAME = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/{{SUMMONER_NAME}}";
    public static final String SUMMONERS_BY_PUUID = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/{{SUMMONER_PUUID}}";
    public static final String SUMMONERS_BY_ACCOUNT = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-account/{{SUMMONER_ACCOUNT}}";
    public static final String SUMMONERS_BY_ID = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/{{SUMMONER_ID}}";
    public static final String MATCHLIST_BY_ACCOUNT = "https://euw1.api.riotgames.com/lol/match/v4/matchlists/by-account/{{SUMMONER_ACCOUNT}}?beginIndex={{BEGIN_INDEX}}";
    public static final String MATCHES = "https://euw1.api.riotgames.com/lol/match/v4/matches/{{GAME_ID}}";
}
