package com.yzx.test;

import com.yzx.entity.MyScores;
import com.yzx.utils.ApplicationEnv;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.util.Collector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import scala.Tuple2;


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
