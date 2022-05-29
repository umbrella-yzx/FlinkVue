package com.yzx.source.redis;

import java.io.Serializable;
import java.util.Map;

/**
 * 定义redis的读取操作
 */
public interface MyRedisCommandsContainer extends Serializable {
    Map<String,String> hget(String key);
    void close();
}