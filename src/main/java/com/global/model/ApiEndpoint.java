package com.global.model;

import javax.persistence.*;


@Entity
@Table(schema = "global")
public class ApiEndpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;

/*
    @Column(nullable = false, unique = true)
    private String endpoint;
    @OneToMany
    private List<Platform> platforms;

    @Column(nullable = false, unique = false)
    private boolean disabled;
/*
    enum ApiMethod
    {
        GET, POST, PUT, DELETE
    }

    @Column(nullable = false, unique = false)
    private ApiMethod method = ApiMethod.GET;

    @Column(nullable = false, unique = false)
    private boolean requiresApiKey;

    @OneToOne
    private RiotGame riotGame;*/

}
