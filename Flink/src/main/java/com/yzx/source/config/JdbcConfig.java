package com.yzx.source.config;

import java.io.Serializable;

public class JdbcConfig implements Serializable {
    private static final long serialVersionUID = 4125096758372084309L;

    private int id;
    private String driverName ="com.mysql.jdbc.Driver";     //驱动名称
    private String url = "jdbc:mysql://localhost:3306/test";  //数据库Url
    private String userName = "root";   //用户名
    private String tableName = "person";    //连接的表名
    private String password = "root";   //密码
    private String exConfig;    //其余配置

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExConfig() {
        return exConfig;
    }

    public void setExConfig(String exConfig) {
        this.exConfig = exConfig;
    }

    public JdbcConfig(String url, String userName, String tableName, String password, String exConfig) {
        this.url = url;
        this.userName = userName;
        this.tableName = tableName;
        this.password = password;
        this.exConfig = exConfig;
    }

    public JdbcConfig(String url, String tableName, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.tableName = tableName;
        this.password = password;
    }

    public JdbcConfig() {
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
