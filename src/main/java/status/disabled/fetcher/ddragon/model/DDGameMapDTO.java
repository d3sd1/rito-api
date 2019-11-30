package status.disabled.fetcher.ddragon.model;

public class DDGameMapDTO {
    private Integer mapId = -1;
    private String mapName = "";
    private String notes = "";

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "DDGameMapDTO{" +
                "mapId=" + mapId +
                ", mapName='" + mapName + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
