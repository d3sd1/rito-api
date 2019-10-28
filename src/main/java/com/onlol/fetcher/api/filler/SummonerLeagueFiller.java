package com.onlol.fetcher.api.filler;

import com.onlol.fetcher.api.model.ApiLeagueItemDTO;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummonerLeagueFiller {
    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private SummonerChampionMasteryRepository summonerChampionMasteryRepository;

    @Autowired
    private SummonerNameHistoricalRepository summonerNameHistoricalRepository;

    @Autowired
    private SummonerTokenRepository summonerTokenRepository;

    @Autowired
    private LeagueRankRepository leagueRankRepository;

    @Autowired
    private SummonerLeagueRepository summonerLeagueRepository;

    @Autowired
    private LogService logger;

    public SummonerLeague fillSummonerLeague(Summoner summoner,
                                             GameQueueType gameQueueType,
                                             LeagueTier leagueTier,
                                             ApiLeagueItemDTO apiLeagueItemDTO) {

        SummonerLeague summonerLeague = this.summonerLeagueRepository.findBySummonerAndGameQueueType(summoner, gameQueueType);
        if (summonerLeague == null) {
            summonerLeague = new SummonerLeague();
            summonerLeague.setSummoner(summoner);
            summonerLeague.setGameQueueType(gameQueueType);
            this.summonerLeagueRepository.save(summonerLeague);
        }
        summonerLeague.setHotStreak(apiLeagueItemDTO.isHotStreak());
        summonerLeague.setWins(apiLeagueItemDTO.getWins());
        summonerLeague.setVeteran(apiLeagueItemDTO.isVeteran());
        summonerLeague.setLosses(apiLeagueItemDTO.getLosses());
        summonerLeague.setInactive(apiLeagueItemDTO.isInactive());
        summonerLeague.setFreshBlood(apiLeagueItemDTO.getFreshBlood());
        summonerLeague.setLeaguePoints(apiLeagueItemDTO.getLeaguePoints());
        summonerLeague.setLeagueTier(leagueTier);
        LeagueRank leagueRank = this.leagueRankRepository.findByKeyName(apiLeagueItemDTO.getRank());
        if (leagueRank == null) {
            leagueRank = new LeagueRank();
            leagueRank.setKeyName(apiLeagueItemDTO.getRank());
            this.leagueRankRepository.save(leagueRank);
        }
        summonerLeague.setLeagueRank(leagueRank);
        return this.summonerLeagueRepository.save(summonerLeague);
    }
}
