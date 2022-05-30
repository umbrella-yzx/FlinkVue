package com.yzx.source.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisClusterConfig;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisConfigBase;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisSentinelConfig;
import org.apache.flink.util.Preconditions;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

/**
 * 根据不同的配置生成不同的对象
 * 这里考虑了直连redis和哨兵模式两种情况，后续还可以考虑redis集群的情形
 */
public class RedisCommandsContainerBuilder {

    public RedisCommandsContainerBuilder(){

    }

    public static RedisCommandsContainer build(FlinkJedisConfigBase flinkJedisConfigBase){
        if(flinkJedisConfigBase instanceof FlinkJedisPoolConfig){
            FlinkJedisPoolConfig flinkJedisPoolConfig = (FlinkJedisPoolConfig) flinkJedisConfigBase;
            return RedisCommandsContainerBuilder.build(flinkJedisPoolConfig);
        } else if (flinkJedisConfigBase instanceof FlinkJedisClusterConfig) {
            FlinkJedisClusterConfig flinkJedisClusterConfig = (FlinkJedisClusterConfig) flinkJedisConfigBase;
            return RedisCommandsContainerBuilder.build(flinkJedisClusterConfig);
        } else if (flinkJedisConfigBase instanceof FlinkJedisSentinelConfig) {
            FlinkJedisSentinelConfig flinkJedisSentinelConfig = (FlinkJedisSentinelConfig) flinkJedisConfigBase;
            return RedisCommandsContainerBuilder.build(flinkJedisSentinelConfig);
        } else {
            throw new IllegalArgumentException("Jedis configuration not found");
        }
    }

    public static RedisCommandsContainer build(FlinkJedisPoolConfig jedisPoolConfig) {
        Preconditions.checkNotNull(jedisPoolConfig, "Redis pool config should not be Null");
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(jedisPoolConfig.getMaxIdle());
        genericObjectPoolConfig.setMaxTotal(jedisPoolConfig.getMaxTotal());
        genericObjectPoolConfig.setMinIdle(jedisPoolConfig.getMinIdle());
        JedisPool jedisPool = new JedisPool(genericObjectPoolConfig, jedisPoolConfig.getHost(), jedisPoolConfig.getPort(), jedisPoolConfig.getConnectionTimeout(), jedisPoolConfig.getPassword(), jedisPoolConfig.getDatabase());
        return new RedisContainer(jedisPool);
    }

    public static RedisCommandsContainer build(FlinkJedisSentinelConfig jedisSentinelConfig) {
        Preconditions.checkNotNull(jedisSentinelConfig, "Redis sentinel config should not be Null");
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(jedisSentinelConfig.getMaxIdle());
        genericObjectPoolConfig.setMaxTotal(jedisSentinelConfig.getMaxTotal());
        genericObjectPoolConfig.setMinIdle(jedisSentinelConfig.getMinIdle());
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(jedisSentinelConfig.getMasterName(), jedisSentinelConfig.getSentinels(), genericObjectPoolConfig, jedisSentinelConfig.getConnectionTimeout(), jedisSentinelConfig.getSoTimeout(), jedisSentinelConfig.getPassword(), jedisSentinelConfig.getDatabase());
        return new RedisContainer(jedisSentinelPool);
    }

}
