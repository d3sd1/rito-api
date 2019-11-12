package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveGameParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne
    private Summoner summoner;

    @OneToOne
    private Champion champion;

    @Column(nullable = false, unique = false)
    private boolean bot;

    @OneToOne
    private SummonerSpell summonerSpell1;

    @OneToOne
    private SummonerSpell summonerSpell2;

    @OneToOne
    private MatchGameTeam team;

    @OneToOne
    private SummonerProfileImage summonerIcon;

    @ManyToMany
    private List<LiveGameParticipantCustomization> gameCustomizationObjects;

    @OneToOne
    private LiveGameParticipantPerks liveGameParticipantPerks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Summoner getSummoner() {
        return summoner;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public SummonerSpell getSummonerSpell1() {
        return summonerSpell1;
    }

    public void setSummonerSpell1(SummonerSpell summonerSpell1) {
        this.summonerSpell1 = summonerSpell1;
    }

    public SummonerSpell getSummonerSpell2() {
        return summonerSpell2;
    }

    public void setSummonerSpell2(SummonerSpell summonerSpell2) {
        this.summonerSpell2 = summonerSpell2;
    }

    public MatchGameTeam getTeam() {
        return team;
    }

    public void setTeam(MatchGameTeam team) {
        this.team = team;
    }

    public SummonerProfileImage getSummonerIcon() {
        return summonerIcon;
    }

    public void setSummonerIcon(SummonerProfileImage summonerIcon) {
        this.summonerIcon = summonerIcon;
    }

    public List<LiveGameParticipantCustomization> getGameCustomizationObjects() {
        return gameCustomizationObjects;
    }

    public void setGameCustomizationObjects(List<LiveGameParticipantCustomization> gameCustomizationObjects) {
        this.gameCustomizationObjects = gameCustomizationObjects;
    }

    public LiveGameParticipantPerks getLiveGameParticipantPerks() {
        return liveGameParticipantPerks;
    }

    public void setLiveGameParticipantPerks(LiveGameParticipantPerks liveGameParticipantPerks) {
        this.liveGameParticipantPerks = liveGameParticipantPerks;
    }
}
