package com.yzx.process;

import com.yzx.source.JdbcSourcewNqsy;
import com.yzx.entity.User;
import com.yzx.entity.ListTest;
import com.yzx.sink.ConsleSinkJqtqA;
import com.yzx.entity.User;
import com.yzx.entity.ListTest;
import com.yzx.source.config.JdbcConfig;
import com.yzx.utils.ApplicationEnv;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.api.java.tuple.*;
import org.apache.flink.streaming.api.datastream.KeyedStream;

import com.yzx.source.redis.RedisCommand;
import com.yzx.source.redis.RedisCommandDescription;
import com.yzx.source.redis.RedisRecord;
import com.yzx.source.redis.RedisSource;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisDataType;
import org.apache.flink.util.Collector;

import com.yzx.source.config.KafkaConfig;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import com.yzx.source.config.HDFSConfig;
import com.yzx.source.config.CSVConfig;
import com.yzx.source.config.RedisConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.flink.streaming.api.functions.sink.filesystem.OutputFileConfig;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.core.fs.Path;
import org.apache.flink.api.common.serialization.Encoder;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class ProcessUackv {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();

        Properties properties = new Properties();

                DataStreamSource<User> Nory9eayt2c = env.addSource(new JdbcSourcewNqsy(new JdbcConfig("jdbc:mysql://192.168.10.1:3306/data", "user", "root", "root")));


























                Nory9eayt2c.print();



        env.execute();
    }
}
