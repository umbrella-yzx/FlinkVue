package com.yzx.domain;

import lombok.Data;

@Data
public class CSVConfig {
    private String path;
    private String exConfig;

    public CSVConfig(String path) {
        this.path = path;
    }

    public CSVConfig(String path, String exConfig) {
        this.path = path;
        this.exConfig = exConfig;
    }
}
