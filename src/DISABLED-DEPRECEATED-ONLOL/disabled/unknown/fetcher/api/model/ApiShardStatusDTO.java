package status.disabled.unknown.fetcher.api.model;

import java.util.ArrayList;
import java.util.List;

/*
/lol/status/v3/shard-data
 */
public class ApiShardStatusDTO {
    private String name = "";
    private String region_tag = "";
    private String hostname = "";
    private List<ApiServiceDTO> services = new ArrayList<>();
    private String slug = "";
    private List<String> locales = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion_tag() {
        return region_tag;
    }

    public void setRegion_tag(String region_tag) {
        this.region_tag = region_tag;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public List<ApiServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<ApiServiceDTO> services) {
        this.services = services;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<String> getLocales() {
        return locales;
    }

    public void setLocales(List<String> locales) {
        this.locales = locales;
    }

    @Override
    public String toString() {
        return "SampleShard{" +
                "name='" + name + '\'' +
                ", region_tag='" + region_tag + '\'' +
                ", hostname='" + hostname + '\'' +
                ", services=" + services +
                ", slug='" + slug + '\'' +
                ", locales=" + locales +
                '}';
    }
}
