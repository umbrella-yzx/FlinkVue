package com.yzx.test;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import scala.Tuple2;


public class RedisPingTest {
    public static void main(String[] args) throws Exception {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        JedisPool jedisPool = new JedisPool(poolConfig, "192.168.10.102", 6379);
        jedisPool.getResource().ping();
    }
}
