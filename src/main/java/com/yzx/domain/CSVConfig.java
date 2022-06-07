package com.yzx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CSVConfig {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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
}
