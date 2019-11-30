package status.disabled.onlol.fetcher.api.endpoints;

public class V4 {
    public static final String SUMMONERS_BY_NAME = "https://{{HOST}}/lol/summoner/v4/summoners/by-name/{{SUMMONER_NAME}}";
    public static final String SUMMONERS_BY_PUUID = "https://{{HOST}}/lol/summoner/v4/summoners/by-puuid/{{SUMMONER_PUUID}}";
    public static final String SUMMONERS_BY_ACCOUNT = "https://{{HOST}}/lol/summoner/v4/summoners/by-account/{{SUMMONER_ACCOUNT}}";
    public static final String SUMMONERS_BY_ID = "https://{{HOST}}/lol/summoner/v4/summoners/{{SUMMONER_ID}}";
    public static final String MATCHLIST_BY_ACCOUNT = "https://{{HOST}}/lol/match/v4/matchlists/by-account/{{SUMMONER_ACCOUNT}}?beginIndex={{BEGIN_INDEX}}";
    public static final String MATCHES = "https://{{HOST}}/lol/match/v4/matches/{{GAME_ID}}";
    public static final String DDRAGON_VERSIONS = "https://ddragon.leagueoflegends.com/api/versions.json";
    public static final String DDRAGON_SEASONS = "http://static.developer.riotgames.com/docs/lol/seasons.json";
    public static final String DDRAGON_QUEUES = "http://static.developer.riotgames.com/docs/lol/queues.json";
    public static final String DDRAGON_MAPS = "http://static.developer.riotgames.com/docs/lol/maps.json";
    public static final String DDRAGON_MODES = "http://static.developer.riotgames.com/docs/lol/gameModes.json";
    public static final String DDRAGON_TYPES = "http://static.developer.riotgames.com/docs/lol/gameTypes.json";
    public static final String DDRAGON_ITEMS = "http://ddragon.leagueoflegends.com/cdn/{{VERSION}}/data/{{LANGUAGE}}/item.json";
    public static final String DDRAGON_CHAMPIONS = "http://ddragon.leagueoflegends.com/cdn/{{VERSION}}/data/{{LANGUAGE}}/champion.json";
    public static final String DDRAGON_LANGUAGES = "https://ddragon.leagueoflegends.com/cdn/languages.json";
    public static final String DDRAGON_REALM = "https://ddragon.leagueoflegends.com/realms/{{REGION}}.json";
    public static final String DDRAGON_SUMMONER_SPELLS = "http://ddragon.leagueoflegends.com/cdn/{{VERSION}}/data/{{LANGUAGE}}/summoner.json";
    public static final String DDRAGON_SUMMONER_IMAGES = "http://ddragon.leagueoflegends.com/cdn/{{VERSION}}/data/{{LANGUAGE}}/profileicon.json";
    public static final String SUMMONER_CHAMPION_MASTERY = "https://{{HOST}}/lol/champion-mastery/v4/champion-masteries/by-summoner/{{SUMMONER_ID}}";
    public static final String LEAGUES_BY_SUMMONER = "https://{{HOST}}/lol/league/v4/entries/by-summoner/{{SUMMONER_ID}}";
    public static final String LEAGUES_CHALLENGER = "https://{{HOST}}/lol/league/v4/challengerleagues/by-queue/{{QUEUE}}";
    public static final String FEATURED_GAMES = "https://{{HOST}}/lol/spectator/v4/featured-games";
    public static final String LEAGUES_GRANDMASTER = "https://{{HOST}}/lol/league/v4/grandmasterleagues/by-queue/{{QUEUE}}";
    public static final String LEAGUES_MASTER = "https://{{HOST}}/lol/league/v4/masterleagues/by-queue/{{QUEUE}}";
    public static final String LEAGUES_BY_ID = "https://{{HOST}}/lol/league/v4/leagues/{{LEAGUE_ID}}";
    public static final String LEAGUES_BY_QUEUE_TIER_DIVISION = "https://{{HOST}}/lol/league/v4/entries/{{LEAGUE_QUEUE}}/{{LEAGUE_TIER}}/{{LEAGUE_DIVISION}}?page={{PAGE_NUMBER}}";
    public static final String SUMMONER_ACTIVE_GAME = "https://{{HOST}}/lol/spectator/v4/active-games/by-summoner/{{SUMMONER_ID}}";
}
