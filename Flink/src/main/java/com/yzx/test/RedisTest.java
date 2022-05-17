package com.yzx.test;

import com.yzx.connector.redis.MyRedisCommand;
import com.yzx.connector.redis.MyRedisCommandDescription;
import com.yzx.connector.redis.MyRedisRecord;
import com.yzx.connector.redis.RedisSource;
import com.yzx.utils.ApplicationEnv;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisDataType;
import org.apache.flink.util.Collector;

import java.util.Map;

public class RedisTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnvironment = ApplicationEnv.getEnvironment();
        executionEnvironment.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        FlinkJedisPoolConfig conf = new FlinkJedisPoolConfig.Builder().setHost("192.168.10.102").setPassword(null).setPort(6379).build();
        DataStreamSource<MyRedisRecord> source = executionEnvironment.addSource(new RedisSource(conf,new MyRedisCommandDescription(MyRedisCommand.HGET,"flink")));
        DataStream<Tuple2<String, Integer>> max = source.flatMap(new MapRedisRecordSplitter()).timeWindowAll(Time.milliseconds(5000)).maxBy(1);
        max.print().setParallelism(1);
        executionEnvironment.execute();
    }

    public static class MapRedisRecordSplitter implements FlatMapFunction<MyRedisRecord, Tuple2<String,Integer>> {
        @Override
        public void flatMap(MyRedisRecord myRedisRecord, Collector<Tuple2<String, Integer>> collector) throws Exception {
            assert myRedisRecord.getRedisDataType() == RedisDataType.HASH;
            Map<String,String> map = (Map<String,String>)myRedisRecord.getData();
            for(Map.Entry<String,String> e : map.entrySet()){
                collector.collect(new Tuple2<>(e.getKey(),Integer.valueOf(e.getValue())));
            }
        }
    }
}
