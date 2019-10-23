package com.onlol.fetcher.api.model;

/*
/lol/match/v4/timelines/by-match/{matchId}
 */
public class ApiMatchPositionDTO {
    private Integer x = 0;
    private Integer y = 0;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "ApiMatchPositionDto{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
