package com.yzx.connector.jdbc;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.yzx.connector.config.JdbcConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.sql.DriverManager;
import java.sql.ResultSet;

public class JdbcSource extends RichSourceFunction<Object> {
    private volatile boolean isRunning = true;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private JdbcConfig jdbcConfig;

    public JdbcSource() {
    }

    public JdbcSource(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Class.forName("com.mysql.jdbc.Driver");  // 加载数据库驱动
        conn = (Connection) DriverManager.getConnection(  // 获取连接
                jdbcConfig.getDBUrl(),  // 数据库URL
                jdbcConfig.getUserName(),  // 用户名
                jdbcConfig.getPassword());  // 登录密码
        ps = (PreparedStatement) conn.prepareStatement(  // 获取执行语句
                "select id,name,age from t_student");  // 需要执行的SQL语句
    }

    @Override
    public void run(SourceContext<Object> sourceContext) throws Exception {
        while(isRunning) {  // 使用while循环可以不断读取数据
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
//                sourceContext.collect(new Student(id,name,age));  // 以流的形式发送结果
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
