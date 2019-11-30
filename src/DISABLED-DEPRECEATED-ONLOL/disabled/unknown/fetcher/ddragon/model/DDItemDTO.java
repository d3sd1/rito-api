package status.disabled.unknown.fetcher.ddragon.model;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class DDItemDTO {
    private String name = "";
    private String description = "";
    private String colloq = "";
    private String plaintext = "";
    private Integer[] into = new Integer[0];
    private DDImageDTO image = new DDImageDTO();
    private DDItemGoldDTO gold = new DDItemGoldDTO();
    private String[] tags = new String[0];
    private LinkedHashMap<Integer, Boolean> maps = new LinkedHashMap<>();
    private LinkedHashMap<String, Double> stats = new LinkedHashMap<>();

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

    public Integer[] getInto() {
        return into;
    }

    public void setInto(Integer[] into) {
        this.into = into;
    }

    public DDImageDTO getImage() {
        return image;
    }

    public void setImage(DDImageDTO image) {
        this.image = image;
    }

    public DDItemGoldDTO getGold() {
        return gold;
    }

    public void setGold(DDItemGoldDTO gold) {
        this.gold = gold;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public LinkedHashMap<Integer, Boolean> getMaps() {
        return maps;
    }

    public void setMaps(LinkedHashMap<Integer, Boolean> maps) {
        this.maps = maps;
    }

    public LinkedHashMap<String, Double> getStats() {
        return stats;
    }

    public void setStats(LinkedHashMap<String, Double> stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        return "SampleItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", colloq='" + colloq + '\'' +
                ", plaintext='" + plaintext + '\'' +
                ", into=" + Arrays.toString(into) +
                ", image=" + image +
                ", gold=" + gold +
                ", tags=" + Arrays.toString(tags) +
                ", maps=" + maps +
                ", stats=" + stats +
                '}';
    }
}
