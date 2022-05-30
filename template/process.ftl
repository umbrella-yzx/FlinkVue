package com.yzx.test.maintest;

<#list process.nodes as node>
import ${node.javaPackage}.${node.className};
<#list node.inPackages as package>
import ${package};
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Process {
    public static void execute() throws Exception{
        StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();
        <#list process.nodes as node>
            <#if node.type == "JdbcSource">
                DataStreamSource<${node.entity.className}> ${node.curName} = env.addSource(new ${node.className}(new JdbcConfig("${node.jdbcConfig.url}", "${node.jdbcConfig.tableName}", "${node.jdbcConfig.userName}", "${node.jdbcConfig.password}")));
            </#if>

            <#if node.type == "CSVSource">
                DataStream<${node.entity.className}> ${node.curName} = new ${node.className}<${node.entity.className}>().getCsvDataStream();
            </#if>

            <#if node.type == "RedisSource">
                SingleOutputStreamOperator<${node.outClass}> ${node.curName} = env.addSource(
                    new RedisSource(
                        new FlinkJedisPoolConfig.Builder().setHost("${node.redisConfig.host}").setPassword(${node.redisConfig.password}).setPort(${node.redisConfig.port}).build(),
                        new RedisCommandDescription(RedisCommand.${node.redisConfig.command},"${node.redisConfig.key}")))
                    .map(new MapFunction<${node.inClass}, ${node.outClass}>() {
                        @Override
                        public ${node.outClass} map(${node.inClass} redisRecord) throws Exception {
                            return (${node.outClass}) redisRecord.getData();
                        }
                    });
            </#if>

            <#if node.type == "Filter">
                SingleOutputStreamOperator<${node.inClass}> ${node.curName} = ${node.preName}.filter(new ${node.className}());
            </#if>

            <#if node.type == "FlatMap">
                SingleOutputStreamOperator<${node.outClass}> ${node.curName} = ${node.preName}.flatMap(new ${node.className}<${node.inClass},${node.outClass}>());
            </#if>

            <#if node.type == "Map">
                SingleOutputStreamOperator<${node.outClass}> ${node.curName} = ${node.preName}.map(new ${node.className}<${node.inClass},${node.outClass}>());
            </#if>

            <#if node.type == "KeySelect">
                KeyedStream<${node.inClass},${node.outClass}> ${node.curName} = ${node.preName}.keyBy(new ${node.className}<${node.inClass},${node.outClass}>());
            </#if>

            <#if node.type == "Reduce">
                SingleOutputStreamOperator<${node.inClass}> ${node.curName} = ${node.preName}.reduce(new ${node.className}<${node.inClass}>());
            </#if>

            <#if node.type == "Aggregation">
                <#if node.aggregationType == 0>
                    SingleOutputStreamOperator<${node.inClass}> ${node.curName} = ${node.preName}.sum(${node.condition});
                </#if>
                <#if node.aggregationType == 1>
                    SingleOutputStreamOperator<${node.inClass}> ${node.curName} = ${node.preName}.min(${node.condition});
                </#if>
                <#if node.aggregationType == 2>
                    SingleOutputStreamOperator<${node.inClass}> ${node.curName} = ${node.preName}.max(${node.condition});
                </#if>
                <#if node.aggregationType == 3>
                    SingleOutputStreamOperator<${node.inClass}> ${node.curName} = ${node.preName}.minBy(${node.condition});
                </#if>
                <#if node.aggregationType == 4>
                    SingleOutputStreamOperator<${node.inClass}> ${node.curName} = ${node.preName}.maxBy(${node.condition});
                </#if>
            </#if>

            <#if node.type == "Union">
                SingleOutputStreamOperator ${node.curName} = ${node.preName}.union(${node.condition});
            </#if>

            <#if node.type == "ConsleSink">
                ${node.preName}.print();
            </#if>
        </#list>

        env.execute();
    }
}