package com.yzx.test;

import com.yzx.source.config.HDFSConfig;
import com.yzx.utils.ApplicationEnv;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class HDFSTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();

        HDFSConfig hdfsConfig = new HDFSConfig();
        DataStreamSource<String> data = env.readTextFile("hdfs://192.168.10.102:8020/test/test.txt");
        data.print();
        env.execute();
    }
}
