package status.disabled.unknown.fetcher.api.filler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import status.disabled.unknown.fetcher.api.model.*;
import status.disabled.unknown.fetcher.ddragon.filler.GameDataFiller;
import status.disabled.unknown.fetcher.ddragon.filler.GameInfoFiller;
import status.disabled.unknown.model.*;
import status.disabled.unknown.repository.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchFiller {
    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private MatchGameRepository matchGameRepository;

    @Autowired
    private MatchListRepository matchListRepository;

    @Autowired
    private MatchGameTeamRepository matchGameTeamRepository;

    @Autowired
    private MatchGameTeamStatsRepository matchGameTeamStatsRepository;

    @Autowired
    private GameDataFiller gameDataFiller;

    @Autowired
    private GameInfoFiller gameInfoFiller;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SummonerTokenRepository summonerTokenRepository;

    @Autowired
    private MatchGameParticipantRepository matchGameParticipantRepository;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private MatchGameTeamBanRepository matchGameTeamBanRepository;


    public MatchList fillMatchListGame(ApiMatchReferenceDTO apiMatchReferenceDto, SummonerToken summonerToken) {

        Summoner summoner = summonerToken.getSummoner();
        MatchList matchList = this.matchListRepository.findByMatchGameIdAndSummoner(apiMatchReferenceDto.getGameId(), summoner);
        if (matchList == null) {
            matchList = new MatchList();

            MatchGame matchGame = this.matchGameRepository.findByGameId(apiMatchReferenceDto.getGameId());
            if (matchGame == null) {
                matchGame = new MatchGame();
                matchGame.setGameId(apiMatchReferenceDto.getGameId());
                matchGame.setRegion(summoner.getRegion());
                this.matchGameRepository.save(matchGame);
            }

            matchList.setMatch(matchGame);

            /* Get queue */
            matchList.setGameQueue(this.gameDataFiller.fillGameQueue(apiMatchReferenceDto.getQueue()));

            /* Get role */
            matchList.setGameRole(this.gameDataFiller.fillGameRole(apiMatchReferenceDto.getRole()));

            /* Get season */
            matchList.setGameSeason(this.gameInfoFiller.fillGameSeason(apiMatchReferenceDto.getSeason()));

            /* Get lane */
            matchList.setGameLane(this.gameInfoFiller.fillGameLane(apiMatchReferenceDto.getLane()));

            /* Get champ */
            matchList.setChamp(this.gameDataFiller.fillChampion(apiMatchReferenceDto.getChampion()));

            matchList.setTimestamp((new Timestamp(apiMatchReferenceDto.getTimestamp()).toLocalDateTime()));
            matchList.setSummoner(summoner);
            this.matchListRepository.save(matchList);
        }
        return matchList;
    }

    public MatchGame fillMatchGame(ApiMatchDTO apiMatchDTO, Region region, ApiKey apiKey) {

        MatchGame matchGame = this.matchGameRepository.findByGameId(apiMatchDTO.getGameId());
        if (matchGame == null) {
            matchGame = new MatchGame();
            matchGame.setGameId(apiMatchDTO.getGameId());
            this.matchGameRepository.save(matchGame);
        }
        matchGame.setGameId(apiMatchDTO.getGameId());
        matchGame.setGameCreation(new

                Timestamp(apiMatchDTO.getGameCreation()).

                toLocalDateTime());
        matchGame.setGameDuration(apiMatchDTO.getGameDuration());
        matchGame.setGameVersion(apiMatchDTO.getGameVersion());


        /* Get queue */
        matchGame.setGameQueue(this.gameDataFiller.fillGameQueue(apiMatchDTO.getQueueId()));


        /* Get season */
        matchGame.setGameSeason(this.gameInfoFiller.fillGameSeason(apiMatchDTO.getSeasonId()));

        /* Get game map */
        matchGame.setGameMap(this.gameDataFiller.fillGameMap(apiMatchDTO.getMapId()));


        /* Get game mode */
        matchGame.setGameMode(this.gameDataFiller.fillGameMode(apiMatchDTO.getGameMode()));


        /* Get game type */
        matchGame.setGameType(this.gameDataFiller.fillGameType(apiMatchDTO.getGameType()));

        matchGame.setRetrieving(false);
        matchGame.setRetrieved(true);

        this.matchGameRepository.save(matchGame);

        /* Add summoners to update */
        for (
                ApiParticipantIdentityDTO apiParticipantIdentityDTO : apiMatchDTO.getParticipantIdentities()) {
            String summonerId = apiParticipantIdentityDTO.getPlayer().getSummonerId();
            String summonerName = apiParticipantIdentityDTO.getPlayer().getSummonerName();

            /*
            First try to reach summoner by id. If null, by name and region.

            Check if user has previous tokens. Use cases:
            1. Hasn't got -> New summoner.
            2. Has >= 1, and internal summoner ID matchs -> update summoner, since it's the same
            3. Has >= 1, and internal summoner ID does not match -> new summoner
             */
            SummonerToken summonerToken = this.summonerTokenRepository.findBySummonerTokenId(summonerId);
            Summoner summoner;
            if (summonerToken == null) { // Could not be reached by summoner id.
                summonerToken = new SummonerToken();
                summonerToken.setApiKey(apiKey);
                summonerToken.setSummonerTokenId(summonerId);
                summoner = this.summonerRepository.findOneByRegionAndName(region, summonerName);
                if (summoner == null) {
                    summoner = new Summoner();
                    summoner.setRegion(region);
                    summoner.setName(summonerName);
                    this.summonerRepository.save(summoner);
                } else {
                    // Get summoner from database for a good linking
                    SummonerToken summonerDbToken = this.summonerTokenRepository.findTopBySummoner(summoner);
                    if (summonerDbToken != null) { // Summoner has tokens
                        summoner = summonerDbToken.getSummoner();
                    }
                }

                summonerToken.setSummoner(summoner);
                this.summonerTokenRepository.save(summonerToken);
            } else {
                summoner = summonerToken.getSummoner();
            }
        }

        /*Match game stats */
        List<MatchGameTeamStats> matchGameTeamStatsList = matchGame.getMatchGameTeamStats();
        if (matchGameTeamStatsList == null) {
            matchGameTeamStatsList = new ArrayList<>();
        }

        for (ApiTeamStatsDTO apiTeamStatsDto : apiMatchDTO.getTeams()) {
            MatchGameTeam matchGameTeam = this.matchGameTeamRepository.findByTeamId(apiTeamStatsDto.getTeamId());
            if (matchGameTeam == null) {
                matchGameTeam = new MatchGameTeam();
                matchGameTeam.setTeamId(apiTeamStatsDto.getTeamId());
                matchGameTeam = this.matchGameTeamRepository.save(matchGameTeam);
            }
            MatchGameTeamStats matchGameTeamStats = new MatchGameTeamStats();

            if (matchGameTeamStats == null) {
                MatchGameTeamStats dbMatchGameTeamStats = new MatchGameTeamStats();
                dbMatchGameTeamStats.setTeam(matchGameTeam);

                matchGameTeamStats = this.matchGameTeamStatsRepository.save(dbMatchGameTeamStats);
            }
            matchGameTeamStats.setFirstDragon(apiTeamStatsDto.isFirstDragon());
            matchGameTeamStats.setFirstInhibitor(apiTeamStatsDto.isFirstInhibitor());
            matchGameTeamStats.setFirstRiftHerald(apiTeamStatsDto.isFirstRiftHerald());
            matchGameTeamStats.setFirstBaron(apiTeamStatsDto.isFirstBaron());
            matchGameTeamStats.setFirstBlood(apiTeamStatsDto.isFirstBlood());
            matchGameTeamStats.setFirstTower(apiTeamStatsDto.isFirstTower());
            matchGameTeamStats.setBaronKills(apiTeamStatsDto.getBaronKills());
            matchGameTeamStats.setRiftHeraldKills(apiTeamStatsDto.getRiftHeraldKills());
            matchGameTeamStats.setVilemawKills(apiTeamStatsDto.getVilemawKills());
            matchGameTeamStats.setInhibitorKills(apiTeamStatsDto.getInhibitorKills());
            matchGameTeamStats.setTowerKills(apiTeamStatsDto.getTowerKills());
            matchGameTeamStats.setDragonKills(apiTeamStatsDto.getDragonKills());
            matchGameTeamStats.setDominionVictoryScore(apiTeamStatsDto.getDominionVictoryScore());
            matchGameTeamStats.setWon(apiTeamStatsDto.getWin().equalsIgnoreCase("Win"));

            /* Fill bans for team just if not set (IMPORTANT) */

            if (matchGameTeamStats.getBans() == null) {
                ArrayList<MatchGameTeamBan> matchGameTeamBans = new ArrayList<>();
                for (ApiTeamBansDTO apiTeamBansDto : apiTeamStatsDto.getBans()) {
                    MatchGameTeamBan matchGameTeamBan = new MatchGameTeamBan();
                    matchGameTeamBan.setPickTurn(apiTeamBansDto.getPickTurn());
                    matchGameTeamBan.setChampion(this.championRepository.findByChampId(apiTeamBansDto.getChampionId()));
                    matchGameTeamBan = this.matchGameTeamBanRepository.save(matchGameTeamBan);
                    matchGameTeamBans.add(matchGameTeamBan);
                }
                matchGameTeamStats.setBans(matchGameTeamBans);
            }


            this.matchGameTeamStatsRepository.save(matchGameTeamStats);
        }
        matchGame.setMatchGameTeamStats(matchGameTeamStatsList);

        /* Fill participants */
        for (
                ApiParticipantIdentityDTO apiParticipantIdentityDto : apiMatchDTO.getParticipantIdentities()) {
            // TODO
            //this.matchGameParticipantRepository.save(matchGameParticipant);
        }
        return null;
    }
}
