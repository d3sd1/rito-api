package com.onlol.fetcher.api.filler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.exceptions.ApiBadRequestException;
import com.onlol.fetcher.exceptions.ApiDownException;
import com.onlol.fetcher.exceptions.ApiUnauthorizedException;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    private Long getSummonerRealId(ApiSummonerDTO apiSummonerDTO, Region region) {
        ApiMatchlistDto apiMatchlistDto;
        Long realMatchId = 0L;
        Long riotRealId = 0L;
        // In case we have no accountId, just return...
        if (apiSummonerDTO.getAccountId() == null || apiSummonerDTO.getAccountId().equals("")) {
            return riotRealId;
        }
        try {
            apiMatchlistDto = this.jacksonMapper.readValue(this.apiConnector.get(
                    V4.MATCHLIST_BY_ACCOUNT
                            .replace("{{SUMMONER_ACCOUNT}}", apiSummonerDTO.getAccountId())
                            .replace("{{HOST}}", region.getHostName())
                            .replace("{{BEGIN_INDEX}}", "0"),
                    true
            ).getJson(), new TypeReference<ApiMatchlistDto>() {
            });
        } catch (DataNotfoundException e) {
            this.logger.info("Data not found, got exception" + e.getMessage());
            return realMatchId;
        } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
            return realMatchId;
        } catch (Exception e) {
            this.logger.error("Got generic exception" + e.getMessage());
            return realMatchId;
        }

        // Has no games... We are not interested on the summoner.
        if (apiMatchlistDto.getMatches().isEmpty()) {
            this.logger.error("Summoner has no matchlist games:" + apiSummonerDTO);
            return realMatchId; // 0
        }

        for (ApiMatchReferenceDTO apiMatchReferenceDTO : apiMatchlistDto.getMatches()) {
            Long gameId = apiMatchReferenceDTO.getGameId();

            ApiMatchDTO apiMatchDTO = new ApiMatchDTO();
            ApiCall apiCall = null;
            try {
                apiCall = this.apiConnector.get(
                        V4.MATCHES.replace("{{GAME_ID}}", String.valueOf(gameId))
                                .replace("{{HOST}}", region.getHostName()),
                        true
                );
                apiMatchDTO = this.jacksonMapper.readValue(apiCall.getJson(), new TypeReference<ApiMatchDTO>() {
                });
            } catch (DataNotfoundException e) {
                e.printStackTrace();
            } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                this.logger.error("Got generic exception" + e.getMessage());
            }
            for (ApiParticipantIdentityDTO apiParticipantIdentityDTO : apiMatchDTO.getParticipantIdentities()) {
                /* If the same summoner to update, return riot real id. */
                String[] riotIdSplitted = apiParticipantIdentityDTO.getPlayer().getMatchHistoryUri().split("/");
                Long currentRiotRealId = Long.parseLong(riotIdSplitted[riotIdSplitted.length - 1]);

                if (apiParticipantIdentityDTO.getPlayer().getSummonerId().equals(apiSummonerDTO.getId())) {
                    riotRealId = currentRiotRealId;
                } else { /* If not, add it to database */
                    this.fillSummoner(apiParticipantIdentityDTO, region, apiCall.getApiKey(), currentRiotRealId);
                }
            }
            return riotRealId;
        }

        return realMatchId;
    }

    public Summoner fillSummoner(ApiParticipantIdentityDTO apiParticipantIdentityDTO, Region region, ApiKey apiKey, Long riotRealId) {
        ApiSummonerDTO apiSummonerDTO = new ApiSummonerDTO();
        apiSummonerDTO.setId(apiParticipantIdentityDTO.getPlayer().getSummonerId());
        apiSummonerDTO.setAccountId(apiParticipantIdentityDTO.getPlayer().getCurrentAccountId());
        apiSummonerDTO.setName(apiParticipantIdentityDTO.getPlayer().getSummonerName());
        apiSummonerDTO.setSummonerLevel(null); // Must be null for re-updating
        apiSummonerDTO.setProfileIconId(null); // Must be null for re-updating
        apiSummonerDTO.setRevisionDate(null); // Must be null for re-updating
        return this.fillSummoner(apiSummonerDTO, region, apiKey, riotRealId);
    }

    public Summoner fillSummoner(ApiLeagueItemDTO apiLeagueItemDTO, Region region, ApiKey apiKey) {
        ApiSummonerDTO apiSummonerDTO = new ApiSummonerDTO();
        apiSummonerDTO.setId(apiLeagueItemDTO.getSummonerId());
        apiSummonerDTO.setName(apiLeagueItemDTO.getSummonerName());
        apiSummonerDTO.setSummonerLevel(null); // Must be null for re-updating
        apiSummonerDTO.setProfileIconId(null); // Must be null for re-updating
        apiSummonerDTO.setRevisionDate(null); // Must be null for re-updating
        return this.fillSummoner(apiSummonerDTO, region, apiKey, 0L);
    }

    public Summoner fillSummoner(ApiSummonerDTO apiSummonerDTO, Region region, ApiKey apiKey) {
        return this.fillSummoner(apiSummonerDTO, region, apiKey, 0L);
    }

    public Summoner fillSummoner(ApiSummonerDTO apiSummonerDTO, Region region, ApiKey apiKey, Long riotRealId) {
        SummonerToken summonerToken = this.summonerTokenRepository.findBySummonerTokenId(apiSummonerDTO.getId());
        Summoner summoner;
        /* Init if needed */
        if (summonerToken == null) {
            summoner = this.summonerRepository.findOneByRegionAndName(region, apiSummonerDTO.getName());
            summonerToken = new SummonerToken();
            summonerToken.setSummonerTokenId(apiSummonerDTO.getId());
            summonerToken.setApiKey(apiKey);
            summonerToken.setSummoner(summoner);
            this.summonerTokenRepository.save(summonerToken);
        } else {
            summoner = summonerToken.getSummoner();
        }

        /* Fullfit summoner token if needed */
        if (apiSummonerDTO.getPuuid() != null && !apiSummonerDTO.getPuuid().equals("")) {
            summonerToken.setPuuTokenId(apiSummonerDTO.getPuuid());
        }
        if (apiSummonerDTO.getAccountId() != null && !apiSummonerDTO.getAccountId().equals("")) {
            summonerToken.setAccountTokenId(apiSummonerDTO.getAccountId());
        }
        this.summonerTokenRepository.save(summonerToken);

        summoner.setName(apiSummonerDTO.getName());
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

        summoner.setRegion(region);
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

        /* Fill riot real id if needed */
        if (riotRealId != 0) {
            summoner.setRiotRealId(riotRealId);
        }
        if (summoner.getRiotRealId() == null || summoner.getRiotRealId() == 0) {
            summoner.setRiotRealId(this.getSummonerRealId(apiSummonerDTO, region));
        }
        this.summonerRepository.save(summoner);

        return summoner;
    }

    public SummonerChampionMastery fillSummonerChampionMastery(Summoner summoner, ApiChampionMasteryDTO apiChampionMasteryDTO) {
        SummonerChampionMastery summonerChampionMastery = this.summonerChampionMasteryRepository.findBySummoner(summoner);
        if (summonerChampionMastery == null) {
            summonerChampionMastery = new SummonerChampionMastery();
        }
        summonerChampionMastery.setSummoner(summoner);
        summonerChampionMastery.setChampion(this.championRepository.findByChampId(apiChampionMasteryDTO.getChampionId()));
        summonerChampionMastery.setChampionLevel(apiChampionMasteryDTO.getChampionLevel());
        summonerChampionMastery.setChampionPoints(apiChampionMasteryDTO.getChampionPoints());
        summonerChampionMastery.setChampionPointsSinceLastLevel(apiChampionMasteryDTO.getChampionPointsSinceLastLevel());
        summonerChampionMastery.setChampionPointsUntilNextLevel(apiChampionMasteryDTO.getChampionPointsUntilNextLevel());
        summonerChampionMastery.setChestGranted(apiChampionMasteryDTO.isChestGranted());
        summonerChampionMastery.setLastPlayTime(new Timestamp(apiChampionMasteryDTO.getLastPlayTime()).toLocalDateTime());
        summonerChampionMastery.setTokensEarned(apiChampionMasteryDTO.getTokensEarned());
        return this.summonerChampionMasteryRepository.save(summonerChampionMastery);
    }
}
