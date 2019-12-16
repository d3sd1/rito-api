package com.global.model;

import javax.persistence.*;


@Entity
@Table(schema = "common")
public class ApiEndpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;

    @Column(nullable = false, unique = false)
    private String endpoint;

    @Column(nullable = false, unique = false)
    private boolean disabled;

    enum ApiMethod
    {
        GET, POST, PUT, DELETE
    }

    @Column(nullable = false, unique = false)
    private ApiMethod method = ApiMethod.GET;

    @Column(nullable = false, unique = false)
    private boolean requiresApiKey;

    @OneToOne
    private RiotGame riotGame;

    @Column(nullable = false, unique = false)
    private boolean countsForRateLimit;

    // URL for the same endpoint on stub (test) mode. Used for tournaments.
    @Column(nullable = true, unique = false)
    private String stub;

}
