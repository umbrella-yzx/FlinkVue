package com.yzx.source.redis;

import org.apache.flink.streaming.connectors.redis.common.mapper.RedisDataType;

import java.io.Serializable;

/**
 * 定义封装redis数据类型和数据对象
 */
public class MyRedisRecord implements Serializable {
    private Object data;
    private RedisDataType redisDataType;

    public MyRedisRecord(Object data, RedisDataType redisDataType) {
        this.data = data;
        this.redisDataType = redisDataType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public RedisDataType getRedisDataType() {
        return redisDataType;
    }

    public void setRedisDataType(RedisDataType redisDataType) {
        this.redisDataType = redisDataType;
    }
}