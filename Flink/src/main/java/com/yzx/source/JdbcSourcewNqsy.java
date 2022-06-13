package com.yzx.source;

import com.yzx.entity.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.yzx.source.config.JdbcConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.api.java.tuple.*;

import java.sql.DriverManager;
import java.sql.ResultSet;

/**
* This code is generated by FreeMarker
*
*/
public class JdbcSourcewNqsy extends RichSourceFunction<User>
{
    private volatile boolean isRunning = true;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private JdbcConfig jdbcConfig;

    public JdbcSourcewNqsy(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Class.forName(jdbcConfig.getDriverName());  // 加载数据库驱动
        conn = (Connection) DriverManager.getConnection(  // 获取连接
            jdbcConfig.getUrl(),  // 数据库URL
            jdbcConfig.getUserName(),  // 用户名
            jdbcConfig.getPassword());  // 登录密码
        ps = (PreparedStatement) conn.prepareStatement(  // 获取执行语句
            "select * from "+jdbcConfig.getTableName());  // 需要执行的SQL语句
    }

    @Override
    public void run(SourceContext<User> sourceContext) throws Exception {
        while(isRunning) {
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                sourceContext.collect(new User(name,age));  // 以流的形式发送结果
            }
            Thread.sleep(5000);  // 每隔5秒查询一次
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }

    @Override
    public void close() throws Exception {
        super.close();
        if(conn != null) conn.close();
        if(ps != null) ps.close();
    }
}