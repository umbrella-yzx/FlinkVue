package com.yzx.test;

import com.yzx.entity.MyScores;
import com.yzx.utils.ApplicationEnv;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;


public class Test {
    public static void main(String[] args) throws Exception {

    }
}
