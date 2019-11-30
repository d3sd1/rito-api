package status.disabled.fetcher.ddragon.model;

import java.util.Arrays;

public class DDItemTreeDTO {
    private String header = "";
    private String[] tags = new String[0];

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "SampleItemTree{" +
                "header='" + header + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
