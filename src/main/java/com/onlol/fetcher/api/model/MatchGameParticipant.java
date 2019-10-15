package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchGameParticipant {
    @Id
    @Column(nullable = false, unique = true)
    private Long gameId;

}
