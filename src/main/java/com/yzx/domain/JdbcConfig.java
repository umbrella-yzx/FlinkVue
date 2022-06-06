package com.yzx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class JdbcConfig {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String driverName ="com.mysql.jdbc.Driver";
    private String DBUrl = "jdbc:mysql://localhost:3306/test";
    private String userName = "root";
    private String tableName = "person";
    private String password = "root";
    private String exConfig;

    public JdbcConfig(String DBUrl, String tableName, String userName, String password) {
        this.DBUrl = DBUrl;
        this.userName = userName;
        this.tableName = tableName;
        this.password = password;
    }
}
