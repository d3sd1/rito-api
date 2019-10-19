package com.onlol.fetcher.api.sampleModel;

import java.util.Arrays;

public class SampleSummonerImageDdragon {
    private String type;
    private String version;
    private SampleSummonerImage[] data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SampleSummonerImage[] getData() {
        return data;
    }

    public void setData(SampleSummonerImage[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SampleSummonerImageDdragon{" +
                "type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
