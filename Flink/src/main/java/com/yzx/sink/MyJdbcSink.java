package com.yzx.sink;

import com.yzx.source.config.JdbcConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MyJdbcSink extends RichSinkFunction<Object> {
    private Connection conn = null;
    private PreparedStatement insertStmt = null;
    private JdbcConfig jdbcConfig;
    // 打开数据库连接，只执行一次，之后一直使用这个连接
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Class.forName(jdbcConfig.getDriverName());  // 加载数据库驱动
        conn = DriverManager.getConnection(  // 获取连接
                jdbcConfig.getUrl(),  // 数据库URL
                jdbcConfig.getUserName(),  // 用户名
                jdbcConfig.getPassword());  // 登录密码
        insertStmt = conn.prepareStatement(  // 获取执行语句
                "insert into " + jdbcConfig.getTableName() +" values (?,?,?)");  // 插入数据
    }

    // 执行插入和更新
    @Override
    public void invoke(Object value, Context ctx) throws Exception {
//        // 如果更新数为0，则执行插入语句
//        insertStmt.setInt(1, value.getId());
//        insertStmt.setString(2, value.getName());
//        insertStmt.setInt(3, value.getAge());
        insertStmt.execute();  // 执行插入语句
    }

    // 关闭数据库连接
    @Override
    public void close() throws Exception {
        super.close();
        if(conn != null) conn.close();
        if(insertStmt != null) insertStmt.close();
    }
}
