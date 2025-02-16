package status.disabled.unknown.fetcher.ddragon.model;

public class DDummonerSpellVariableDTO {
    private String link = "";
    private Object coeff = new Object();
    private String key = "";

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Object getCoeff() {
        return coeff;
    }

    public void setCoeff(Object coeff) {
        this.coeff = coeff;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "SampleSummonerSpellVariable{" +
                "link='" + link + '\'' +
                ", coeff=" + coeff +
                ", key='" + key + '\'' +
                '}';
    }
}
