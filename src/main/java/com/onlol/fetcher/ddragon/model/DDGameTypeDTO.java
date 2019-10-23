package com.onlol.fetcher.ddragon.model;

public class DDGameTypeDTO {
    private String gametype = ""; // lowercase is a MUST!!
    private String description = "";

    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DDGameTypeDTO{" +
                "gameType='" + gametype + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
