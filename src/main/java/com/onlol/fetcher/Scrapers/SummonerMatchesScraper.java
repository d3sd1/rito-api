package com.onlol.fetcher.Scrapers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SummonerMatchesScraper {
    @Scheduled(fixedRate = 5000)
    public void ret() {
        /*System.out.println("Fetching data");
        RestTemplate restTemplate = new RestTemplate();


        MatchList quote = restTemplate.getForObject("https://euw1.api.riotgames.com/lol/match/v4/matchlists/by-account/XHuGvAypHqg760gTgF5DVQXbPY6g1Bv38xATlBHt9cTh4M8?api_key=RGAPI-a5435ca3-ad49-4a9d-97a1-c42341da504f", MatchList.class);
        System.out.println(quote);

        ResponseEntity<List<MatchList>> rateResponse =
                restTemplate.exchange("https://euw1.api.riotgames.com/lol/match/v4/matchlists/by-account/XHuGvAypHqg760gTgF5DVQXbPY6g1Bv38xATlBHt9cTh4M8?api_key=RGAPI-a5435ca3-ad49-4a9d-97a1-c42341da504f",
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });
        List<MatchList> matches = rateResponse.getBody();
        System.out.println(matches);*/

    }
}
