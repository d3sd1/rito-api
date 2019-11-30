package status.disabled.fetcher.ddragon.model;

public class DDDdragonDTO<T> {
    private String type = "";
    private String format = "";
    private String version = "";
    private T data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SampleDdragon{" +
                "type='" + type + '\'' +
                ", format='" + format + '\'' +
                ", version='" + version + '\'' +
                ", data=" + data +
                '}';
    }
}
