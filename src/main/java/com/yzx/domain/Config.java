package com.yzx.domain;

import lombok.Data;

@Data
public class Config{
    CSVConfig csvConfig;
    JdbcConfig jdbcConfig;
    HDFSConfig hdfsConfig;
    KafkaConfig kafkaConfig;
    RedisConfig redisConfig;

    //Config类型
    String type;
    //数据源类型
    String state;
    //数据结构定义
    String dataResourceDingyi;
    //数据源周期类型
    String dataResourceZhouqiState;
}
