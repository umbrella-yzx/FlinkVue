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
        StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();
        StreamTableEnvironment tableEnv = ApplicationEnv.getTableEnvironment();
        String sql = "CREATE TABLE mytable("+
                " `id` INT not null," +
                " `name` STRING not null," +
                " `chinese` INT not null," +
                " `english` INT not null," +
                " `math` INT not null" +
                ")WITH("+
                " 'connector' = 'filesystem',"+
                " 'path' = 'input/scores.csv',"+
                " 'format' = 'csv'"+
                ")";
        TableResult tableResult = tableEnv.executeSql(sql);
        tableEnv.executeSql("DROP TABLE IF EXISTS `mytable`");
        sql = "CREATE TABLE mytable("+
                " `id` INT not null," +
                " `name` STRING not null," +
                " `chinese` INT not null," +
                " `english` INT not null," +
                " `math` INT not null" +
                ")WITH("+
                " 'connector' = 'filesystem',"+
                " 'path' = 'input/scores.csv',"+
                " 'format' = 'csv'"+
                ")";
        tableEnv.executeSql(sql);
        Table table = tableEnv.sqlQuery("select * from mytable");
        DataStream<MyScores> sinkStream = tableEnv.toAppendStream(table, MyScores.class);
        sinkStream.print();
        env.execute();
    }
}
