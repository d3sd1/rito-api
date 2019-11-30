package status.disabled.onlol.fetcher.ddragon.model;

import java.util.Arrays;

public class DDSummonerImageDdragonDTO {
    private String type = "";
    private String version = "";
    private DDSummonerImageDTO[] data;

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

    public DDSummonerImageDTO[] getData() {
        return data;
    }

    public void setData(DDSummonerImageDTO[] data) {
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
