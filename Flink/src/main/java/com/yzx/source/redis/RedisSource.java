package com.yzx.source.redis;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisConfigBase;
import org.apache.flink.util.Preconditions;


/**
 * redis source的实现
 */
public class RedisSource extends RichSourceFunction<RedisRecord>{

    private static final long serialVersionUID = 1L;
    private String additionalKey;
    private RedisCommand redisCommand;
    private FlinkJedisConfigBase flinkJedisConfigBase;
    private RedisCommandsContainer redisCommandsContainer;
    private volatile boolean isRunning = true;

    /**
     *
     * @param flinkJedisConfigBase redis配置信息
     * @param redisCommandDescription 要读取的redis数据类型信息
     */
    public RedisSource(FlinkJedisConfigBase flinkJedisConfigBase, RedisCommandDescription redisCommandDescription) {
        Preconditions.checkNotNull(flinkJedisConfigBase, "Redis connection pool config should not be null");
        Preconditions.checkNotNull(redisCommandDescription, "MyRedisCommandDescription  can not be null");
        this.flinkJedisConfigBase = flinkJedisConfigBase;
        this.redisCommand = redisCommandDescription.getCommand();
        this.additionalKey = redisCommandDescription.getAdditionalKey();
    }


    /**
     * 在source打开执行，用了完成redis操作类对象的创建
     * @param parameters
     * @throws Exception
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        this.redisCommandsContainer = RedisCommandsContainerBuilder.build(this.flinkJedisConfigBase);
    }

    /**
     * 一直读取redis数据，并根据数据类型调用对应的redis操作，封装成RedisRecord对象，够后续处理
     * @param sourceContext
     * @throws Exception
     */
    @Override
    public void run(SourceContext sourceContext) throws Exception {
        while (isRunning){
            switch(this.redisCommand) {
                case HGET:
                    sourceContext.collect(new RedisRecord(this.redisCommandsContainer.hget(this.additionalKey), this.redisCommand.getRedisDataType()));
                    break;
                case GET:
                    sourceContext.collect(new RedisRecord(this.redisCommandsContainer.get(this.additionalKey), this.redisCommand.getRedisDataType()));
                    break;
                case LRANGE:
                    sourceContext.collect(new RedisRecord(this.redisCommandsContainer.lrange(this.additionalKey), this.redisCommand.getRedisDataType()));
                    break;
                case ZRANGE:
                    sourceContext.collect(new RedisRecord(this.redisCommandsContainer.zrange(this.additionalKey), this.redisCommand.getRedisDataType()));
                    break;
                case HGETALL:
                    sourceContext.collect(new RedisRecord(this.redisCommandsContainer.hgetAll(this.additionalKey), this.redisCommand.getRedisDataType()));
                    break;
                case PFCOUNT:
                    sourceContext.collect(new RedisRecord(this.redisCommandsContainer.pfcount(this.additionalKey), this.redisCommand.getRedisDataType()));
                    break;
                case SMEMBERS:
                    sourceContext.collect(new RedisRecord(this.redisCommandsContainer.smembers(this.additionalKey), this.redisCommand.getRedisDataType()));
                    break;
                case ZRANGEWITHSCORES:
                    sourceContext.collect(new RedisRecord(this.redisCommandsContainer.zrangeWithScores(this.additionalKey), this.redisCommand.getRedisDataType()));
                    break;
                default:
                    throw new IllegalArgumentException("Cannot process such data type: " + this.redisCommand);
            }
            Thread.sleep(5000);
        }

    }

    @Override
    public void cancel()  {
        isRunning = false;
        if (this.redisCommandsContainer != null) {
            this.redisCommandsContainer.close();
        }
    }
}