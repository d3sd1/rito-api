package com.onlol.fetcher.api.sampleModel;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class SampleItemSet {
    private String type;
    private String version;
    private LinkedHashMap<Integer, SampleItem> data;
    private SampleItemGroup[] groups;
    private SampleItemTree[] tree;

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

    public LinkedHashMap<Integer, SampleItem> getData() {
        return data;
    }

    public void setData(LinkedHashMap<Integer, SampleItem> data) {
        this.data = data;
    }

    public SampleItemGroup[] getGroups() {
        return groups;
    }

    public void setGroups(SampleItemGroup[] groups) {
        this.groups = groups;
    }

    public SampleItemTree[] getTree() {
        return tree;
    }

    public void setTree(SampleItemTree[] tree) {
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
