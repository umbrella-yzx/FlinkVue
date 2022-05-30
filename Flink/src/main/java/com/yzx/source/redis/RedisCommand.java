package com.yzx.source.redis;

import org.apache.flink.streaming.connectors.redis.common.mapper.RedisDataType;


/**
 * 操作类型
 */
public enum RedisCommand {
    //获取指定 key 的值。
    GET(RedisDataType.STRING),
    //获取存储在哈希表中指定字段的值。
    HGET(RedisDataType.HASH),
    //以列表形式返回哈希表的字段及字段值。
    HGETALL(RedisDataType.HASH),
    //获取列表所有值
    LRANGE(RedisDataType.LIST),
    //集合中的所有成员
    SMEMBERS(RedisDataType.SET),
    //指定区间内，不带有分数值(可选)的有序集成员的列表
    ZRANGE(RedisDataType.SORTED_SET),
    //指定区间内，带有分数值(可选)的有序集成员的列表
    ZRANGEWITHSCORES(RedisDataType.LIST),
    //返回给定 HyperLogLog 的基数估算值
    PFCOUNT(RedisDataType.HYPER_LOG_LOG);

    private RedisDataType redisDataType;

    private RedisCommand(RedisDataType redisDataType) {
        this.redisDataType = redisDataType;
    }

    public RedisDataType getRedisDataType() {
        return this.redisDataType;
    }
}
