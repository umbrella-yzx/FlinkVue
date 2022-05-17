package com.yzx.connector.config;

public class CSVConfig {
    private String path;
    private String exConfig;

    public CSVConfig() {
    }

    public CSVConfig(String path) {
        this.path = path;
    }

    public CSVConfig(String path, String exConfig) {
        this.path = path;
        this.exConfig = exConfig;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExConfig() {
        return exConfig;
    }

    public void setExConfig(String exConfig) {
        this.exConfig = exConfig;
    }

    @Override
    public String toString() {
        return "CSVConfig{" +
                "filePath='" + path + '\'' +
                '}';
    }
}
