package com.onlol.fetcher.ddragon.model;

import java.util.Arrays;

public class DDChampionDTO {
    private String id = "";
    private String key = "";
    private String version = "";
    private String name = "";
    private String title = "";
    private String blurb = "";
    private String partype = "";
    private String[] tags = new String[]{};
    private DDChampionInformationDTO info = new DDChampionInformationDTO();
    private DDChampionStatsDTO stats = new DDChampionStatsDTO();

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

    public DDChampionInformationDTO getInfo() {
        return info;
    }

    public void setInfo(DDChampionInformationDTO info) {
        this.info = info;
    }

    public DDChampionStatsDTO getStats() {
        return stats;
    }

    public void setStats(DDChampionStatsDTO stats) {
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
