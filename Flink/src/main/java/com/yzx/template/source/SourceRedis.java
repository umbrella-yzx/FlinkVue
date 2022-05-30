package com.yzx.template.source;

import com.yzx.source.config.RedisConfig;
import com.yzx.template.Node;

public class SourceRedis extends Node {
    //redis配置项
    public RedisConfig redisConfig;
    //redis输入类型
    public String inClass = "RedisRecord";
    //redis输出类型
    public String outClass;

    public SourceRedis(RedisConfig redisConfig) {
        javaPackage = "com.yzx.source";
        className = "RedisSource";
        type = "RedisSource";

        this.redisConfig = redisConfig;

        switch (redisConfig.getCommand()){
            case "HGET":this.outClass = "Map<String,String>";break;
            case "GET":this.outClass = "String";break;
            case "HGETALL":this.outClass = "Map<String,String>";break;
            case "LRANGE":this.outClass = "List<String>";break;
            case "SMEMBERS":this.outClass = "List<String>";break;
            case "ZRANGE":this.outClass = "List<String>";break;
            case "ZRANGEWITHSCORES":this.outClass = "List<Tuple2>";break;
            case "PFCOUNT":this.outClass = "Long";break;
            default:break;
        }
    }

    public RedisConfig getRedisConfig() {
        return redisConfig;
    }

    public void setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }
}
