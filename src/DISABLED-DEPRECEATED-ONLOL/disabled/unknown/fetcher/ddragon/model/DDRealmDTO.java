package status.disabled.unknown.fetcher.ddragon.model;

public class DDRealmDTO {
    private DDnDTO n = new DDnDTO();
    private String v = "";
    private String l = "";
    private String cdn = "";
    private String dd = "";
    private String lg = "";
    private String css = "";
    private Integer profileiconmax = 0;
    private String store = "";

    public DDnDTO getN() {
        return n;
    }

    public void setN(DDnDTO n) {
        this.n = n;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getLg() {
        return lg;
    }

    public void setLg(String lg) {
        this.lg = lg;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public Integer getProfileiconmax() {
        return profileiconmax;
    }

    public void setProfileiconmax(Integer profileiconmax) {
        this.profileiconmax = profileiconmax;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "SampleRealm{" +
                "n=" + n +
                ", v='" + v + '\'' +
                ", l='" + l + '\'' +
                ", cdn='" + cdn + '\'' +
                ", dd='" + dd + '\'' +
                ", lg='" + lg + '\'' +
                ", css='" + css + '\'' +
                ", profileiconmax=" + profileiconmax +
                ", store='" + store + '\'' +
                '}';
    }
}
