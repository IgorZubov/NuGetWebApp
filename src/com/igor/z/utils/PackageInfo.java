package com.igor.z.utils;

public class PackageInfo {
    private String name;
    private String version;
    private String description;

    public PackageInfo(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
