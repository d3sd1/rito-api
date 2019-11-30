package status.disabled.onlol.fetcher.api.model;

import java.util.ArrayList;
import java.util.List;

/*
/lol/status/v3/shard-data
 */
public class ApiMessageDTO {
    private String severity = "";
    private String author = "";
    private String created_at = "";
    private List<ApiTranslationDTO> translations = new ArrayList<>();
    private String updated_at = "";
    private String content = "";
    private String id = "";

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<ApiTranslationDTO> getTranslations() {
        return translations;
    }

    public void setTranslations(List<ApiTranslationDTO> translations) {
        this.translations = translations;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SampleShardMessage{" +
                "severity='" + severity + '\'' +
                ", author='" + author + '\'' +
                ", created_at='" + created_at + '\'' +
                ", translations=" + translations +
                ", updated_at='" + updated_at + '\'' +
                ", content='" + content + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
