package status.disabled.unknown.fetcher.ddragon.model;

public class DDSummonerImageDTO {
    private Integer id = 0;
    private DDImageDTO image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DDImageDTO getImage() {
        return image;
    }

    public void setImage(DDImageDTO image) {
        this.image = image;
    }
}
