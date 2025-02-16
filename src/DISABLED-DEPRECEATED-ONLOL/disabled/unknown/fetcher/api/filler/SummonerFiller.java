package status.disabled.unknown.fetcher.api.filler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import status.disabled.unknown.fetcher.api.ApiConnector;
import status.disabled.unknown.fetcher.api.model.ApiLeagueItemDTO;
import status.disabled.unknown.fetcher.api.model.ApiParticipantIdentityDTO;
import status.disabled.unknown.fetcher.api.model.ApiSummonerDTO;
import status.disabled.unknown.fetcher.logger.LogService;
import status.disabled.unknown.model.ApiKey;
import status.disabled.unknown.model.Summoner;
import status.disabled.unknown.model.SummonerNameHistorical;
import status.disabled.unknown.model.SummonerToken;
import status.disabled.unknown.repository.*;

import java.time.LocalDateTime;

@Service
public class SummonerFiller {
    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private SummonerChampionMasteryRepository summonerChampionMasteryRepository;

    @Autowired
    private SummonerNameHistoricalRepository summonerNameHistoricalRepository;

    @Autowired
    private SummonerTokenRepository summonerTokenRepository;

    @Autowired
    private ObjectMapper jacksonMapper;

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private LogService logger;

    @Autowired
    private MatchFiller matchFiller;


    public SummonerToken fillSummoner(ApiParticipantIdentityDTO apiParticipantIdentityDTO, Summoner summoner, ApiKey apiKey) {
        ApiSummonerDTO apiSummonerDTO = new ApiSummonerDTO();
        apiSummonerDTO.setId(apiParticipantIdentityDTO.getPlayer().getSummonerId());
        apiSummonerDTO.setAccountId(apiParticipantIdentityDTO.getPlayer().getCurrentAccountId());
        apiSummonerDTO.setName(apiParticipantIdentityDTO.getPlayer().getSummonerName());
        apiSummonerDTO.setSummonerLevel(null); // Must be null for re-updating
        apiSummonerDTO.setProfileIconId(null); // Must be null for re-updating
        apiSummonerDTO.setRevisionDate(null); // Must be null for re-updating
        return this.fillSummoner(apiSummonerDTO, summoner, apiKey);
    }

    public SummonerToken fillSummoner(ApiLeagueItemDTO apiLeagueItemDTO, Summoner summoner, ApiKey apiKey) {
        ApiSummonerDTO apiSummonerDTO = new ApiSummonerDTO();
        apiSummonerDTO.setId(apiLeagueItemDTO.getSummonerId());
        apiSummonerDTO.setName(apiLeagueItemDTO.getSummonerName());
        apiSummonerDTO.setSummonerLevel(null); // Must be null for re-updating
        apiSummonerDTO.setProfileIconId(null); // Must be null for re-updating
        apiSummonerDTO.setRevisionDate(null); // Must be null for re-updating
        return this.fillSummoner(apiSummonerDTO, summoner, apiKey);
    }

    public SummonerToken fillSummoner(ApiSummonerDTO apiSummonerDTO, Summoner summoner, ApiKey apiKey) {
        SummonerToken summonerToken = this.summonerTokenRepository.findBySummonerTokenId(apiSummonerDTO.getId());
        /* Init if needed */
        if (summonerToken == null) {
            summoner = this.summonerRepository.findOneByRegionAndName(summoner.getRegion(), apiSummonerDTO.getName());
            summonerToken = new SummonerToken();
            summonerToken.setSummonerTokenId(apiSummonerDTO.getId());
            summonerToken.setApiKey(apiKey);
            this.summonerTokenRepository.save(summonerToken);
        } else {
            summoner = summonerToken.getSummoner();
        }

        if (summoner == null) {
            summoner = new Summoner();
        }

        /* Fill summoner at first */

        summoner.setName(apiSummonerDTO.getName());
        boolean firstTime = true;
        if (apiSummonerDTO.getProfileIconId() != null) {
            summoner.setProfileIconId(apiSummonerDTO.getProfileIconId());
            firstTime = false;
        }
        if (apiSummonerDTO.getSummonerLevel() != null) {
            summoner.setSummonerLevel(apiSummonerDTO.getSummonerLevel());
            firstTime = false;
        }
        if (apiSummonerDTO.getRevisionDate() != null) {
            summoner.setRevisionDate(apiSummonerDTO.getRevisionDate());
            firstTime = false;
        }
        /* Set prev date, so we queue this summoner for being updated. */
        if (firstTime) {
            summoner.setLastTimeUpdated(LocalDateTime.of(2010, 9, 9, 0, 0));
        } else {
            summoner.setLastTimeUpdated(LocalDateTime.now());
        }

        summoner = this.summonerRepository.save(summoner);

        /* Fullfit summoner historical names */
        SummonerNameHistorical summonerNameHistorical =
                this.summonerNameHistoricalRepository.findTopByNameAndSummoner(apiSummonerDTO.getName(), summoner);
        if (summonerNameHistorical == null) {
            summonerNameHistorical = new SummonerNameHistorical();
            summonerNameHistorical.setName(apiSummonerDTO.getName());
            summonerNameHistorical.setSummoner(summoner);
            summonerNameHistorical.setTimestamp(LocalDateTime.now());
            this.summonerNameHistoricalRepository.save(summonerNameHistorical);
        }

        /* Fullfit summoner token if needed */
        if (apiSummonerDTO.getPuuid() != null && !apiSummonerDTO.getPuuid().equals("")) {
            summonerToken.setPuuTokenId(apiSummonerDTO.getPuuid());
        }
        if (apiSummonerDTO.getAccountId() != null && !apiSummonerDTO.getAccountId().equals("")) {
            summonerToken.setAccountTokenId(apiSummonerDTO.getAccountId());
        }
        summonerToken.setSummoner(summoner);
        this.summonerTokenRepository.save(summonerToken);

        return summonerToken;
    }
}
