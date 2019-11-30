package com.onlol.database.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameItemLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne
    private Language language;

    @OneToOne
    private GameItem gameItem;

    @Column(nullable = false, unique = false)
    private String name = "";

    @Column(nullable = false, unique = false, columnDefinition = "text")
    private String description = "";

    @Column(nullable = false, unique = false)
    private String colloq = "";

    @Column(nullable = false, unique = false, columnDefinition = "text")
    private String plaintext = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public GameItem getGameItem() {
        return gameItem;
    }

    public void setGameItem(GameItem gameItem) {
        this.gameItem = gameItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColloq() {
        return colloq;
    }

    public void setColloq(String colloq) {
        this.colloq = colloq;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    @Override
    public String toString() {
        return "GameItemLanguage{" +
                "id=" + id +
                ", language=" + language +
                ", gameItem=" + gameItem +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", colloq='" + colloq + '\'' +
                ", plaintext='" + plaintext + '\'' +
                '}';
    }
}
