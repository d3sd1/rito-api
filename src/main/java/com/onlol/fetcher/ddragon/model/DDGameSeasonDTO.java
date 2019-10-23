package com.onlol.fetcher.ddragon.model;

public class DDGameSeasonDTO {
    private Integer id;
    private String season;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    @Override
    public String toString() {
        return "DDGameSeasonDTO{" +
                "id=" + id +
                ", season='" + season + '\'' +
                '}';
    }
}
