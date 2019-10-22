package com.onlol.fetcher.api.riotModel;

public class SampleRegionShardTranslation {
    private String locale;
    private String content;
    private String updated_at;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "SampleShardTranslation{" +
                "locale='" + locale + '\'' +
                ", content='" + content + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
