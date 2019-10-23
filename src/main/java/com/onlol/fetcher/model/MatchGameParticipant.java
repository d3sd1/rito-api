package com.onlol.fetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchGameParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne
    private MatchGame matchGame;

    @OneToOne
    private Summoner summoner; // FROM participantId

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MatchGame getMatchGame() {
        return matchGame;
    }

    public void setMatchGame(MatchGame matchGame) {
        this.matchGame = matchGame;
    }

    public Summoner getSummoner() {
        return summoner;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

    /*
    TODO: agregar estos datos aqui en matchconnector linera 432, pero primero se requieren datos runes y spells agregados a db
    stats	ParticipantStatsDto	Participant statistics.
    runes	List[RuneDto]	List of legacy Rune information. Not included for matches played with Runes Reforged.
    timeline	ParticipantTimelineDto	Participant timeline data.
            teamId	int	100 for blue side. 200 for red side.
    spell2Id	int	Second Summoner Spell id.
    masteries	List[MasteryDto]	List of legacy Mastery information. Not included for matches played with Runes Reforged.
    highestAchievedSeasonTier	string	Highest ranked tier achieved for the previous season in a specific subset of queueIds, if any, otherwise null. Used to display border in game loading screen. Please refer to the Ranked Info documentation. (Legal values: CHALLENGER, MASTER, DIAMOND, PLATINUM, GOLD, SILVER, BRONZE, UNRANKED)
    spell1Id	int	First Summoner Spell id.
            championId	int*/
}
