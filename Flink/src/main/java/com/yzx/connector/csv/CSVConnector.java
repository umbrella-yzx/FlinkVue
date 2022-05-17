package com.yzx.connector.csv;

import com.yzx.connector.IConnector;
import com.yzx.connector.config.CSVConfig;
import com.yzx.utils.ApplicationEnv;
import com.yzx.utils.MyStringUtil;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class CSVConnector implements IConnector {
    private CSVConfig csvConfig;
    private StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();
    private StreamTableEnvironment tableEnv = ApplicationEnv.getTableEnvironment();

    public CSVConnector() {
    }

    public CSVConnector(CSVConfig csvConfig) {
        this.csvConfig = csvConfig;
    }

    @Override
    public TableResult connect(String sql) {
        String connectSql = sql+
                "WITH("+ MyStringUtil.ConfigParse(csvConfig.getExConfig()) +
                " 'connector' = 'filesystem',"+
                " 'path' = '"+csvConfig.getPath()+"',"+
                " 'format' = 'csv')";
        return tableEnv.executeSql(connectSql);
    }

    public CSVConfig getCsvConfig() {
        return csvConfig;
    }

    public void setCsvConfig(CSVConfig csvConfig) {
        this.csvConfig = csvConfig;
    }
}
