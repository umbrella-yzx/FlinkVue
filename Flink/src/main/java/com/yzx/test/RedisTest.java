package com.yzx.test;

import com.yzx.source.redis.RedisCommand;
import com.yzx.source.redis.RedisCommandDescription;
import com.yzx.source.redis.RedisRecord;
import com.yzx.source.redis.RedisSource;
import com.yzx.utils.ApplicationEnv;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisDataType;
import org.apache.flink.util.Collector;

import java.util.Map;

public class RedisTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnvironment = ApplicationEnv.getEnvironment();
//        FlinkJedisPoolConfig conf = new FlinkJedisPoolConfig.Builder().setHost("192.168.10.102").setPassword(null).setPort(6379).build();

        //测试hget

        SingleOutputStreamOperator<String> map = executionEnvironment.addSource(
                new RedisSource(
                        new FlinkJedisPoolConfig.Builder().setHost("192.168.10.102").setPassword(null).setPort(6379).build(),
                        new RedisCommandDescription(RedisCommand.HGET,"flink")))
                .map(new MapFunction<RedisRecord, String>() {
            @Override
            public String map(RedisRecord redisRecord) throws Exception {
                return (String) redisRecord.getData();
            }
        });
//
//        DataStream<Tuple2<String, Integer>> max = source.flatMap(new MapRedisRecordSplitter()).timeWindowAll(Time.milliseconds(5000)).maxBy(1);
//        max.print().setParallelism(1);

//        //测试get
//        DataStreamSource<RedisRecord> source = executionEnvironment.addSource(new RedisSource(conf,new RedisCommandDescription(RedisCommand.GET,"flink")));
//        SingleOutputStreamOperator<String> map = source.map(new MapFunction<RedisRecord, String>() {
//            @Override
//            public String map(RedisRecord redisRecord) throws Exception {
//                return (String) redisRecord.getData();
//            }
//        });
//        map.print();

//        //测试hgetAll
//        DataStreamSource<RedisRecord> source = executionEnvironment.addSource(new RedisSource(conf,new RedisCommandDescription(RedisCommand.HGETALL,"flinkAll")));
//        SingleOutputStreamOperator<Map<String,String>> map = source.map(new MapFunction<RedisRecord, Map<String,String>>() {
//            @Override
//            public Map<String,String> map(RedisRecord redisRecord) throws Exception {
//                return (Map<String,String>) redisRecord.getData();
//            }
//        });
//        map.print();

//        //测试LRANGE
//        DataStreamSource<RedisRecord> source = executionEnvironment.addSource(new RedisSource(conf,new RedisCommandDescription(RedisCommand.LRANGE,"flinkList")));
//        SingleOutputStreamOperator<List<String>> map = source.map(new MapFunction<RedisRecord, List<String>>() {
//            @Override
//            public List<String> map(RedisRecord redisRecord) throws Exception {
//                return (List<String>) redisRecord.getData();
//            }
//        });
//        map.print();


//        //测试SMEMBERS
//        DataStreamSource<RedisRecord> source = executionEnvironment.addSource(new RedisSource(conf,new RedisCommandDescription(RedisCommand.SMEMBERS,"flinkSet")));
//        SingleOutputStreamOperator<List<String>> map = source.map(new MapFunction<RedisRecord, List<String>>() {
//            @Override
//            public List<String> map(RedisRecord redisRecord) throws Exception {
//                return (List<String>) redisRecord.getData();
//            }
//        });
//        map.print();


//        //测试ZRANGE
//        DataStreamSource<RedisRecord> source = executionEnvironment.addSource(new RedisSource(conf,new RedisCommandDescription(RedisCommand.ZRANGE,"flinkZrange")));
//        SingleOutputStreamOperator<List<String>> map = source.map(new MapFunction<RedisRecord, List<String>>() {
//            @Override
//            public List<String> map(RedisRecord redisRecord) throws Exception {
//                return (List<String>) redisRecord.getData();
//            }
//        });
//        map.print();

//        //测试ZRANGEWITHSCORES
//        DataStreamSource<RedisRecord> source = executionEnvironment.addSource(new RedisSource(conf,new RedisCommandDescription(RedisCommand.ZRANGEWITHSCORES,"flinkZrange")));
//        SingleOutputStreamOperator<List<Tuple2>> map = source.map(new MapFunction<RedisRecord, List<Tuple2>>() {
//            @Override
//            public List<Tuple2> map(RedisRecord redisRecord) throws Exception {
//                return (List<Tuple2>) redisRecord.getData();
//            }
//        });
//        map.print();

//        //测试PFCOUNT
//        DataStreamSource<RedisRecord> source = executionEnvironment.addSource(new RedisSource(conf,new RedisCommandDescription(RedisCommand.PFCOUNT,"flinkpf")));
//        SingleOutputStreamOperator<Long> map = source.map(new MapFunction<RedisRecord, Long>() {
//            @Override
//            public Long map(RedisRecord redisRecord) throws Exception {
//                return (Long) redisRecord.getData();
//            }
//        });
//        map.print();



        executionEnvironment.execute();
    }

    public static class MapRedisRecordSplitter implements FlatMapFunction<RedisRecord, Tuple2<String,Integer>> {
        @Override
        public void flatMap(RedisRecord redisRecord, Collector<Tuple2<String, Integer>> collector) throws Exception {
            assert redisRecord.getRedisDataType() == RedisDataType.HASH;
            Map<String,String> map = (Map<String,String>) redisRecord.getData();
            for(Map.Entry<String,String> e : map.entrySet()){
                collector.collect(new Tuple2<>(e.getKey(),Integer.valueOf(e.getValue())));
            }
        }
    }
}
