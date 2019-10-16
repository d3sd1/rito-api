package com.onlol.fetcher.api.sampleModel;

import java.util.Arrays;

public class SampleChampion {
    private String id;
    private String key;
    private String version;
    private String name;
    private String title;
    private String blurb;
    private String partype;
    private String[] tags;
    private SampleChampionInformation info;
    private SampleChampionStats stats;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SampleChampionInformation getInfo() {
        return info;
    }

    public void setInfo(SampleChampionInformation info) {
        this.info = info;
    }

    public SampleChampionStats getStats() {
        return stats;
    }

    public void setStats(SampleChampionStats stats) {
        this.stats = stats;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getPartype() {
        return partype;
    }

    public void setPartype(String partype) {
        this.partype = partype;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "SampleChampion{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", version='" + version + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", blurb='" + blurb + '\'' +
                ", partype='" + partype + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", info=" + info +
                ", stats=" + stats +
                '}';
    }
}
