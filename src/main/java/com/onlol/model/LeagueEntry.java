/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.onlol.deserializer.LeagueEntryDeserializer;

import javax.persistence.*;

/**
 * League Entry model.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "onlol")
@JsonDeserialize(using = LeagueEntryDeserializer.class)
public class LeagueEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column()
    private short lp = 0;

    @Column()
    private boolean freshBlood = false;

    @Column()
    private boolean inactive = false;

    @Column()
    private boolean veteran = false;

    @Column()
    private boolean hotStreak = false;

    @Column()
    private int losses = 0;

    @Column()
    private int wins = 0;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.REMOVE)
    private LeagueEntryMiniSeries miniSeries;

    @OneToOne(orphanRemoval = false)
    private LeagueTier leagueTier;

    @OneToOne(orphanRemoval = false)
    private League league;

    @OneToOne(orphanRemoval = false)
    private LeagueDivision leagueDivision;

    @OneToOne(orphanRemoval = false)
    private Queue queue;

    @OneToOne(orphanRemoval = false)
    private Summoner summoner;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets lp.
     *
     * @return the lp
     */
    public short getLp() {
        return lp;
    }

    /**
     * Sets lp.
     *
     * @param lp the lp
     */
    public void setLp(short lp) {
        this.lp = lp;
    }

    /**
     * Is fresh blood boolean.
     *
     * @return the boolean
     */
    public boolean isFreshBlood() {
        return freshBlood;
    }

    /**
     * Sets fresh blood.
     *
     * @param freshBlood the fresh blood
     */
    public void setFreshBlood(boolean freshBlood) {
        this.freshBlood = freshBlood;
    }

    /**
     * Is inactive boolean.
     *
     * @return the boolean
     */
    public boolean isInactive() {
        return inactive;
    }

    /**
     * Sets inactive.
     *
     * @param inactive the inactive
     */
    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    /**
     * Is veteran boolean.
     *
     * @return the boolean
     */
    public boolean isVeteran() {
        return veteran;
    }

    /**
     * Sets veteran.
     *
     * @param veteran the veteran
     */
    public void setVeteran(boolean veteran) {
        this.veteran = veteran;
    }

    /**
     * Gets losses.
     *
     * @return the losses
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Sets losses.
     *
     * @param losses the losses
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }

    /**
     * Gets wins.
     *
     * @return the wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * Sets wins.
     *
     * @param wins the wins
     */
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * Is hot streak boolean.
     *
     * @return the boolean
     */
    public boolean isHotStreak() {
        return hotStreak;
    }

    /**
     * Sets hot streak.
     *
     * @param hotStreak the hot streak
     */
    public void setHotStreak(boolean hotStreak) {
        this.hotStreak = hotStreak;
    }

    /**
     * Gets mini series.
     *
     * @return the mini series
     */
    public LeagueEntryMiniSeries getMiniSeries() {
        return miniSeries;
    }

    /**
     * Sets mini series.
     *
     * @param miniSeries the mini series
     */
    public void setMiniSeries(LeagueEntryMiniSeries miniSeries) {
        this.miniSeries = miniSeries;
    }

    /**
     * Gets league tier.
     *
     * @return the league tier
     */
    public LeagueTier getLeagueTier() {
        return leagueTier;
    }

    /**
     * Sets league tier.
     *
     * @param leagueTier the league tier
     */
    public void setLeagueTier(LeagueTier leagueTier) {
        this.leagueTier = leagueTier;
    }

    /**
     * Gets league.
     *
     * @return the league
     */
    public League getLeague() {
        return league;
    }

    /**
     * Sets league.
     *
     * @param league the league
     */
    public void setLeague(League league) {
        this.league = league;
    }

    /**
     * Gets league division.
     *
     * @return the league division
     */
    public LeagueDivision getLeagueDivision() {
        return leagueDivision;
    }

    /**
     * Sets league division.
     *
     * @param leagueDivision the league division
     */
    public void setLeagueDivision(LeagueDivision leagueDivision) {
        this.leagueDivision = leagueDivision;
    }

    /**
     * Gets queue.
     *
     * @return the queue
     */
    public Queue getQueue() {
        return queue;
    }

    /**
     * Sets queue.
     *
     * @param queue the queue
     */
    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    /**
     * Gets summoner.
     *
     * @return the summoner
     */
    public Summoner getSummoner() {
        return summoner;
    }

    /**
     * Sets summoner.
     *
     * @param summoner the summoner
     */
    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

    @Override
    public String toString() {
        return "LeagueEntry{" +
                "id=" + id +
                ", lp=" + lp +
                ", freshBlood=" + freshBlood +
                ", inactive=" + inactive +
                ", veteran=" + veteran +
                ", hotStreak=" + hotStreak +
                ", losses=" + losses +
                ", wins=" + wins +
                ", miniSeries=" + miniSeries +
                ", leagueTier=" + leagueTier +
                ", league=" + league +
                ", leagueDivision=" + leagueDivision +
                ", queue=" + queue +
                ", summoner=" + summoner +
                '}';
    }
}
