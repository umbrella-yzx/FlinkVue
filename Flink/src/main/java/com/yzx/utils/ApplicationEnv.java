package com.yzx.utils;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class ApplicationEnv {
    private static StreamExecutionEnvironment env;
    private static StreamTableEnvironment tableEnv;
    private static StreamExecutionEnvironment remoteEnv;
    private ApplicationEnv(){

    }

    public static StreamExecutionEnvironment getEnvironment() {
        if (env == null) {
            env = StreamExecutionEnvironment.getExecutionEnvironment();
        }
        return env;
    }

    public static StreamExecutionEnvironment getRemoteEnvironment() {
        if (remoteEnv == null) {
            remoteEnv = StreamExecutionEnvironment.createRemoteEnvironment("192.168.10.102", 8081, "E:\\Java\\Workspace\\FlinkVue\\Flink\\target\\Flink-1.0-SNAPSHOT-jar-with-dependencies.jar");
        }
        return remoteEnv;
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
