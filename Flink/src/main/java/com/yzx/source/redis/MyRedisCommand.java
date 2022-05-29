package com.yzx.source.redis;

import org.apache.flink.streaming.connectors.redis.common.mapper.RedisDataType;


/**
 * 操作类型
 */
public enum MyRedisCommand {
    HGET(RedisDataType.HASH);

    private RedisDataType redisDataType;

    private MyRedisCommand(RedisDataType redisDataType) {
        this.redisDataType = redisDataType;
    }

    public RedisDataType getRedisDataType() {
        return this.redisDataType;
    }
}
