package com.yzx.template.sink;

import com.yzx.source.config.JdbcConfig;
import com.yzx.template.Node;
import com.yzx.template.entity.Entity;

public class SinkJdbc extends Node {
    //Jdbc配置项
    public JdbcConfig jdbcConfig;
    //数据表对应POJO类
    public Entity entity;
    //sql插入语句
    public String sql;

    public SinkJdbc() {
        javaPackage = "com.yzx.sink";
        className = "JdbcSink";
        type = "JdbcSink";
    }

    public JdbcConfig getJdbcConfig() {
        return jdbcConfig;
    }

    public void setJdbcConfig(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
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
