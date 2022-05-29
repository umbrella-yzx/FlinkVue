package com.yzx.template.source;

import com.yzx.source.config.JdbcConfig;
import com.yzx.template.Node;
import com.yzx.template.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class SourceJdbc extends Node{
    //Jdbc配置项
    public JdbcConfig jdbcConfig;
    //数据表对应POJO类
    public Entity entity;

    public SourceJdbc() {
        javaPackage = "com.yzx.source";
        className = "JdbcSource";
        type = "JdbcSource";
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
}
