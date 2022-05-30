package com.yzx.source.redis;

import org.apache.flink.api.java.tuple.Tuple2;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 定义redis的读取操作
 */
public interface RedisCommandsContainer extends Serializable {
    Map<String,String> hget(String key);
    List<String> lrange(String key);
    String get(String key);
    Map<String,String> hgetAll(String key);
    List<String> smembers(String key);
    List<String> zrange(String key);
    List<Tuple2> zrangeWithScores(String key);
    long pfcount(String key);
    void close();
}