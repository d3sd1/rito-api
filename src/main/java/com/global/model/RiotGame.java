package com.global.model;

import javax.persistence.*;


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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getDescription() {
        return description;
    }

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
