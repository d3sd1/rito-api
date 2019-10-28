package com.onlol.fetcher.api.filler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlol.fetcher.api.ApiConnector;
import com.onlol.fetcher.api.bypass.SummonerBypass;
import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.exceptions.ApiBadRequestException;
import com.onlol.fetcher.exceptions.ApiDownException;
import com.onlol.fetcher.exceptions.ApiUnauthorizedException;
import com.onlol.fetcher.exceptions.DataNotfoundException;
import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.model.*;
import com.onlol.fetcher.repository.SummonerNameHistoricalRepository;
import com.onlol.fetcher.repository.SummonerRepository;
import com.onlol.fetcher.repository.SummonerTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SummonerFiller {
    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private SummonerBypass summonerBypass;

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
            return realMatchId; // 0
        }

        for (ApiMatchReferenceDTO apiMatchReferenceDTO : apiMatchlistDto.getMatches()) {
            Long gameId = apiMatchReferenceDTO.getGameId();

            ApiMatchDTO apiMatchDTO = new ApiMatchDTO();

            try {
                apiMatchDTO = this.jacksonMapper.readValue(this.apiConnector.get(
                        V4.MATCHES.replace("{{GAME_ID}}", String.valueOf(gameId))
                                .replace("{{HOST}}", region.getHostName()),
                        true
                ).getJson(), new TypeReference<ApiMatchlistDto>() {
                });
            } catch (DataNotfoundException e) {

            } catch (ApiBadRequestException | ApiUnauthorizedException | ApiDownException e) {

            } catch (Exception e) {
                this.logger.error("Got generic exception" + e.getMessage());
            }
            for (ApiParticipantIdentityDTO apiParticipantIdentityDTO : apiMatchDTO.getParticipantIdentities()) {

            }
        }

        return realMatchId;
    }
    // TODO agrupar mails error

    public Summoner fillSummoner(ApiCall apiCall, ApiSummonerDTO apiSummonerDTO) {
            /*
            Pasos 1: Crear/recuperar summoner
            paso 2: crear summoner id si no existe
            paso 3: crear mapa de hash a summoner y api
             */
            /*
        CASOS:
        1. no existe summoner en db y no existe summoner hash con api
        2. existe summoner en db y no existe usmmoner hash con api
        3. existe summoner en db y existe summoner hash con api
     */

        // Caso 3 - fastest
        Summoner summoner;
        SummonerToken summonerPrevToken = this.summonerTokenRepository.findBySummonerTokenId(apiSummonerDTO.getId());
        if (summonerPrevToken == null) {
            summonerPrevToken = new SummonerToken();
            summonerPrevToken.setPuuTokenId(apiSummonerDTO.getPuuid());
            summonerPrevToken.setSummonerTokenId(apiSummonerDTO.getId());
            summonerPrevToken.setApiKey(apiCall.getApiKey());
            summonerPrevToken.setAccountTokenId(apiSummonerDTO.getAccountId());

            summoner = this.summonerRepository.findByRiotRealId(this.getSummonerRealId());
            if (summoner == null || summoner.getRiotRealId() == 0) {
                return null;
            }
            summonerPrevToken.setSummoner(summoner);
            this.summonerTokenRepository.save(summonerPrevToken);
        } else {
            return summonerPrevToken.getSummoner();
        }

        summoner.setLastTimeUpdated(LocalDateTime.now());
        summoner = this.summonerRepository.save(summoner);
        // Update historical name if needed
        if (this.summonerNameHistoricalRepository.findTopByNameAndSummoner(apiSummonerDTO.getName(), summoner) == null) {
            SummonerNameHistorical summonerNameHistorical = new SummonerNameHistorical();
            summonerNameHistorical.setName(summoner.getName());
            summonerNameHistorical.setSummoner(summoner);
            this.summonerNameHistoricalRepository.save(summonerNameHistorical);
        }
        return summoner;
    }

    public void fillSummonerChampionMastery() {/* TODO
        SummonerChampionMastery summonerChampionMastery = this.summonerChampionMasteryRepository.findBySummoner(summoner);
        if (summonerChampionMastery == null) {
            summonerChampionMastery = new SummonerChampionMastery();
        }
        summonerChampionMastery.setSummoner(summoner);
        //summonerChampionMastery.setChampion(this.championRepository.findByChampId(apiChampionMasteryDTO.getChampionId()));
        summonerChampionMastery.setChampionLevel(apiChampionMasteryDTO.getChampionLevel());
        //summonerChampionMastery.setChampionPoints(apiChampionMasteryDTO.getChampionPoints());
        summonerChampionMastery.setChampionPointsSinceLastLevel(apiChampionMasteryDTO.getChampionPointsSinceLastLevel());
        summonerChampionMastery.setChampionPointsUntilNextLevel(apiChampionMasteryDTO.getChampionPointsUntilNextLevel());
        summonerChampionMastery.setChestGranted(apiChampionMasteryDTO.isChestGranted());
        summonerChampionMastery.setLastPlayTime(new Timestamp(apiChampionMasteryDTO.getLastPlayTime()).toLocalDateTime());
        summonerChampionMastery.setTokensEarned(apiChampionMasteryDTO.getTokensEarned());
        this.summonerChampionMasteryRepository.save(summonerChampionMastery);*/
    }
}
