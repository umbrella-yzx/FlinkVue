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

import java.util.ArrayList;
import java.util.List;

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

            <#if node.type == "Filter">
                SingleOutputStreamOperator<${node.inClass}> ${node.curName} = ${node.preName}.filter(new ${node.className}());
            </#if>

        </#list>
        filter1.print();
        env.execute();
    }
}