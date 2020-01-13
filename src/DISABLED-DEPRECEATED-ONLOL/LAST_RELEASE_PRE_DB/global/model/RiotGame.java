/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.model;

import javax.persistence.*;


/**
 * Riot Game model. Used for store different Riot Games.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "common")
public class RiotGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;

    @Column(nullable = false, unique = true)
    private String gameName;

    @Column(nullable = false, unique = true)
    private String description;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets game name.
     *
     * @return the game name
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets game name.
     *
     * @param gameName the game name
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RiotGame{" +
                "id=" + id +
                ", gameName='" + gameName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object riotGame) {
        boolean retVal = false;

        if (riotGame instanceof RiotGame) {
            RiotGame ptr = (RiotGame) riotGame;
            retVal = ptr.getId() == this.id;
        }

        return retVal;
    }
}
