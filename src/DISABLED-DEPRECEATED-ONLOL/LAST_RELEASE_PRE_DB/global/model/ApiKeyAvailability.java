/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.transaction.Transactional;

/**
 * Api key availability for determined region and riot game. It gets disabled by 403 responses.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "common")
@JsonIgnoreProperties(ignoreUnknown = true)
@Transactional
public class ApiKeyAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @OneToOne
    private Platform platform;

    @OneToOne
    private RiotGame riotGame;

    /**
     * Gets platform.
     *
     * @return the platform
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Sets platform.
     *
     * @param platform the platform
     */
    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets riot game.
     *
     * @return the riot game
     */
    public RiotGame getRiotGame() {
        return riotGame;
    }

    /**
     * Sets riot game.
     *
     * @param riotGame the riot game
     */
    public void setRiotGame(RiotGame riotGame) {
        this.riotGame = riotGame;
    }

    @Override
    public String toString() {
        return "ApiKeyAvailability{" +
                "id=" + id +
                ", platform=" + platform +
                ", riotGame=" + riotGame +
                '}';
    }
}
