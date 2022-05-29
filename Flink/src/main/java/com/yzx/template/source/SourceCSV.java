package com.yzx.template.source;

import com.yzx.source.config.CSVConfig;
import com.yzx.source.config.JdbcConfig;
import com.yzx.template.Node;
import com.yzx.template.entity.Entity;

import java.util.List;

public class SourceCSV extends Node {
    //CSV配置项
    public CSVConfig csvConfig;
    //CSV对应POJO类
    public Entity entity;
    //建立表的sql语句
    public String sql;

    public SourceCSV() {
        javaPackage = "com.yzx.source";
        className = "CSVSource";
        type = "CSVSource";
    }

    public CSVConfig getCsvConfig() {
        return csvConfig;
    }

    public void setCsvConfig(CSVConfig csvConfig) {
        this.csvConfig = csvConfig;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
