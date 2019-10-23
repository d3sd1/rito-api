package com.onlol.fetcher.ddragon.model;

import java.util.Arrays;
import java.util.LinkedHashMap;

//TODO: use DDragon T
public class DDItemSetDTO {
    private String type = "";
    private String version = "";
    private LinkedHashMap<Integer, DDItemDTO> data = new LinkedHashMap<>();
    private DDItemGroupDTO[] groups = new DDItemGroupDTO[]{};
    private DDItemTreeDTO[] tree = new DDItemTreeDTO[]{};

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

    public LinkedHashMap<Integer, DDItemDTO> getData() {
        return data;
    }

    public void setData(LinkedHashMap<Integer, DDItemDTO> data) {
        this.data = data;
    }

    public DDItemGroupDTO[] getGroups() {
        return groups;
    }

    public void setGroups(DDItemGroupDTO[] groups) {
        this.groups = groups;
    }

    public DDItemTreeDTO[] getTree() {
        return tree;
    }

    public void setTree(DDItemTreeDTO[] tree) {
        this.tree = tree;
    }

    @Override
    public String toString() {
        return "SampleItemSet{" +
                "type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", data=" + data +
                ", groups=" + Arrays.toString(groups) +
                ", tree=" + Arrays.toString(tree) +
                '}';
    }
}
