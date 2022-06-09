package ${process.javaPackage};

<#list process.nodes as node>
<#if (node.javaPackage)??>import ${node.javaPackage}.${node.className};</#if>
<#list node.inPackages as package>
<#if (package)??>import ${package};</#if>
</#list>
</#list>
import com.yzx.source.config.JdbcConfig;
import com.yzx.utils.ApplicationEnv;
import com.yzx.utils.Utils;
import com.yzx.template.entity.Entity;
import com.yzx.template.entity.Property;
import com.yzx.template.entity.PropertyType;
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

public class Process {
    public static void execute() throws Exception{
        StreamExecutionEnvironment env = ApplicationEnv.getRemoteEnvironment();

        Properties properties = new Properties();

        <#list process.nodes as node>
            <#if node.type == "JdbcSource">
                DataStreamSource<${node.entity.className}> ${node.curName} = env.addSource(new ${node.className}(new JdbcConfig("${node.jdbcConfig.url}", "${node.jdbcConfig.tableName}", "${node.jdbcConfig.userName}", "${node.jdbcConfig.password}")));
            </#if>

            <#if node.type == "CSVSource">
                DataStream<${node.entity.className}> ${node.curName} = new ${node.className}().getCsvDataStream();
            </#if>

            <#if node.type == "HDFSSource">
                DataStreamSource<${node.outClass}> ${node.curName} = env.readTextFile("hdfs://"+"${node.hdfsConfig.host}"+":"+"${node.hdfsConfig.port?c}"+"${node.hdfsConfig.filePath}");
            </#if>

            <#if node.type == "RedisSource">
                SingleOutputStreamOperator<${node.outClass}> ${node.curName} = env.addSource(
                    new RedisSource(
                        new FlinkJedisPoolConfig.Builder().setHost("${node.redisConfig.host}").setPassword(${node.redisConfig.password}).setPort(${node.redisConfig.port?c}).build(),
                        new RedisCommandDescription(RedisCommand.${node.redisConfig.command},"${node.redisConfig.key}")))
                    .map(new MapFunction<${node.inClass}, ${node.outClass}>() {
                        @Override
                        public ${node.outClass} map(${node.inClass} redisRecord) throws Exception {
                            return (${node.outClass}) redisRecord.getData();
                        }
                    });
            </#if>

            <#if node.type == "KafkaSource">
                <#list node.kafkaConfig.properties as property>
                properties.setProperty("${property.key}","${property.value}");
                </#list>
                DataStream<${node.outClass}> ${node.curName} = env
                        .addSource(new FlinkKafkaConsumer<${node.outClass}>("${node.kafkaConfig.topic}", new SimpleStringSchema(), (Properties) properties.clone()));
                properties.clear();
            </#if>

            <#if node.type == "Filter">
                SingleOutputStreamOperator<${node.outClass}> ${node.curName} = ${node.preName}.filter(new ${node.className}());
            </#if>

            <#if node.type == "FlatMap">
                SingleOutputStreamOperator<${node.outClass}> ${node.curName} = ${node.preName}.flatMap(new ${node.className}());
            </#if>

            <#if node.type == "Map">
                SingleOutputStreamOperator<${node.outClass}> ${node.curName} = ${node.preName}.map(new ${node.className}());
            </#if>

            <#if node.type == "KeySelect">
                KeyedStream<${node.inClass},${node.outClass}> ${node.curName} = ${node.preName}.keyBy(new ${node.className}());
            </#if>

            <#if node.type == "Reduce">
                SingleOutputStreamOperator<${node.outClass}> ${node.curName} = ${node.preName}.reduce(new ${node.className}());
            </#if>

            <#if node.type == "Aggregation">
                <#if node.aggregationType == 0>
                    SingleOutputStreamOperator<${node.outClass}> ${node.curName} = ${node.preName}.sum(${node.condition});
                </#if>
                <#if node.aggregationType == 1>
                    SingleOutputStreamOperator<${node.outClass}> ${node.curName} = ${node.preName}.min(${node.condition});
                </#if>
                <#if node.aggregationType == 2>
                    SingleOutputStreamOperator<${node.outClass}> ${node.curName} = ${node.preName}.max(${node.condition});
                </#if>
                <#if node.aggregationType == 3>
                    SingleOutputStreamOperator<${node.outClass}> ${node.curName} = ${node.preName}.minBy(${node.condition});
                </#if>
                <#if node.aggregationType == 4>
                    SingleOutputStreamOperator<${node.outClass}> ${node.curName} = ${node.preName}.maxBy(${node.condition});
                </#if>
            </#if>

            <#if node.type == "Union">
                DataStream ${node.curName} = ${node.preName}.union(${node.condition});
            </#if>

            <#if node.type == "ConsleSink">
                ${node.preName}.print();
<#--                ${node.preName}.addSink(new ${node.className}());-->
            </#if>

            <#if node.type == "JdbcSink">
                ${node.preName}.addSink(new ${node.className}(new JdbcConfig("${node.jdbcConfig.url}", "${node.jdbcConfig.tableName}", "${node.jdbcConfig.userName}", "${node.jdbcConfig.password}")));
            </#if>

            <#if node.type == "CSVSink">
                ${node.preName}.addSink(StreamingFileSink.forRowFormat(new Path("${node.csvConfig.path}"),
                                    new Encoder<${node.outClass}>() {
                                        @Override
                                        public void encode(${node.outClass} value, OutputStream outputStream) throws IOException {
                                            Charset charset = Charset.forName("UTF-8");
                                            outputStream.write(value.<#if node.isentity>toCSVString()<#else>toString()</#if>.getBytes(charset));
                                            outputStream.write(10);
                                        }
                                        })
                                        .withRollingPolicy(
                                            DefaultRollingPolicy.builder()
                                            //文件滚动间隔 每隔多久（指定）时间生成一个新文件
                                            .withRolloverInterval(TimeUnit.SECONDS.toMillis(5))
                                            //数据不活动时间 每隔多久（指定）未来活动数据，则将上一段时间（无数据时间段）也生成一个文件
                                            .withInactivityInterval(TimeUnit.MINUTES.toMillis(5))
                                            // 文件最大容量
                                            .withMaxPartSize(1024 * 1024 * 1024)
                                            .build())
                                        .withOutputFileConfig(OutputFileConfig
                                            .builder()
                                            .withPartSuffix(".csv")
                                            .build())
                                            .build())
                                .setParallelism(1);
            </#if>
        </#list>

        env.execute();
    }
}
