package com.yzx.process;

import com.yzx.utils.ApplicationEnv;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class GroupFunction implements IFunction{
    private StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();
    private StreamTableEnvironment tableEnv = ApplicationEnv.getTableEnvironment();

    @Override
    public Table sqlQuery(String sql) {
        return tableEnv.sqlQuery(sql);
    }

    @Override
    public TableResult executeSql(String sql) {
        return tableEnv.executeSql(sql);
    }
}
