package com.yzx.utils;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class ApplicationEnv {
    private static StreamExecutionEnvironment env;
    private static StreamTableEnvironment tableEnv;
    private ApplicationEnv(){}

    public static StreamExecutionEnvironment getEnvironment() {
        if (env == null) {
            env = StreamExecutionEnvironment.getExecutionEnvironment();
        }
        return env;
    }

    public static StreamTableEnvironment getTableEnvironment() {
        if (env == null) {
            env = StreamExecutionEnvironment.getExecutionEnvironment();
        }
        if (tableEnv == null) {
            EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
            tableEnv = StreamTableEnvironment.create(env, settings);
        }
        return tableEnv;
    }
}
