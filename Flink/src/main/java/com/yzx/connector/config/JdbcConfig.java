package com.yzx.connector.config;

import com.alibaba.fastjson.JSONObject;

public class JdbcConfig {
    private int id;
    private String driverName ="com.mysql.jdbc.Driver";
    private String DBUrl = "jdbc:mysql://localhost:3306/test";
    private String userName = "root";
    private String tableName = "person";
    private String password = "root";
    private String exConfig;

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

    public JdbcConfig(String DBUrl, String userName, String tableName, String password, String exConfig) {
        this.DBUrl = DBUrl;
        this.userName = userName;
        this.tableName = tableName;
        this.password = password;
        this.exConfig = exConfig;
    }

    public JdbcConfig(String DBUrl, String tableName, String userName, String password) {
        this.DBUrl = DBUrl;
        this.userName = userName;
        this.tableName = tableName;
        this.password = password;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDBUrl() {
        return DBUrl;
    }

    public void setDBUrl(String DBUrl) {
        this.DBUrl = DBUrl;
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

    public JSONObject toJsonObject(){
        JSONObject obj=new JSONObject();//创建JSONObject对象
        obj.put("driverName",driverName);
        obj.put("DBUrl",DBUrl);
        obj.put("userName",userName);
        obj.put("tableName",tableName);
        obj.put("password",password);
        return obj;
    }
}
