package com.onlol.fetcher.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onlol.fetcher.api.sampleModel.SampleRegionShardTranslation;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionShardMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = false)
    private String severity;

    @Column(nullable = false, unique = false)
    private String author;

    @Column(nullable = false, unique = false)
    private LocalDateTime createdAt;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<RegionShardTranslation> translations;

    @Column(nullable = false, unique = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false, unique = false)
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<RegionShardTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<RegionShardTranslation> translations) {
        this.translations = translations;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "RegionShardMessage{" +
                "id=" + id +
                ", severity='" + severity + '\'' +
                ", author='" + author + '\'' +
                ", createdAt=" + createdAt +
                ", translations=" + translations +
                ", updatedAt='" + updatedAt + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
