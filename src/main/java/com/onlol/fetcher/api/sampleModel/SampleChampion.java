package com.onlol.fetcher.api.sampleModel;

public class SampleChampion {
    private String id;
    private String key;
    private String version;
    private String name;
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

    @Override
    public String toString() {
        return "SampleChampion{" +
                "id='" + id + '\'' +
                ", key=" + key +
                ", version='" + version + '\'' +
                ", name='" + name + '\'' +
                ", info=" + info +
                ", stats=" + stats +
                '}';
    }
}
