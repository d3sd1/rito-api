package com.onlol.fetcher.ddragon.model;

public class DDGameTypeDTO {
    private String gameType = "";
    private String description = "";

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
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
                "gameType='" + gameType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
