package com.onlol.fetcher.api.connector;

import com.onlol.fetcher.api.endpoints.V4;
import com.onlol.fetcher.api.model.*;
import com.onlol.fetcher.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class DdragonConnector {

    @Autowired
    private LoLVersionRepository loLVersionRepository;

    @Autowired
    private SummonerRepository summonerRepository;

    public ArrayList<LoLVersion> versions() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList<String>> resp = restTemplate.exchange(
                V4.DDRAGON_VERSIONS,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<ArrayList<String>>() {
                });
        ArrayList<String> stringVersions = resp.getBody();
        Collections.reverse(stringVersions);
        ArrayList<LoLVersion> loLVersions = new ArrayList<>();

        for(String stringVersion:stringVersions) {
            LoLVersion dbVersion = this.loLVersionRepository.findByVersion(stringVersion);
            if(dbVersion != null) {
                loLVersions.add(dbVersion);
               continue;
            }
            LoLVersion version = new LoLVersion();
            version.setVersion(stringVersion);
            this.loLVersionRepository.save(version);
            loLVersions.add(version);
        }
        return loLVersions;
    }
}
