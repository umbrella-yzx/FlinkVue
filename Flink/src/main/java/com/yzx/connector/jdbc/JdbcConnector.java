package com.yzx.connector.jdbc;

import com.yzx.connector.IConnector;
import com.yzx.connector.config.JdbcConfig;
import com.yzx.utils.ApplicationEnv;
import com.yzx.utils.MyStringUtil;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class JdbcConnector implements IConnector {
    private JdbcConfig jdbcConfig;
    private StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();
    private StreamTableEnvironment tableEnv = ApplicationEnv.getTableEnvironment();

    public JdbcConnector() {
    }

    public JdbcConnector(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    public JdbcConfig getJdbcConfig() {
        return jdbcConfig;
    }

    public void setJdbcConfig(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    @Override
    public TableResult connect(String sql) {
        String connectSql = sql+
                "WITH("+MyStringUtil.ConfigParse(jdbcConfig.getExConfig()) +
                " 'connector' = 'jdbc',"+
                " 'url' = '"+ jdbcConfig.getDBUrl() +"',"+
                " 'table-name' = '"+jdbcConfig.getTableName()+"',"+
                " 'username' = '"+ jdbcConfig.getUserName() +"',"+
                " 'password' = '"+ jdbcConfig.getPassword() +"')";
        return tableEnv.executeSql(connectSql);
    }
}
