package com.yzx.test;

import com.yzx.template.Process;
import com.yzx.template.sink.SinkCSV;
import com.yzx.template.sink.SinkConsle;
import com.yzx.template.sink.SinkJdbc;
import com.yzx.utils.Utils;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class NodeTest {
    public static void main(String[] args) throws Exception {
        String entityJson = "[\n" +
                "    {\n" +
                "        \"name\": \"User\",\n" +
                "        \"ziduan\": [\n" +
                "            {\n" +
                "                \"name\": \"name\",\n" +
                "                \"type\": \"String\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"age\",\n" +
                "                \"type\": \"Int\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]";



        String json = "{\n" +
                "    \"name\": \"默认\",\n" +
                "    \"nodeList\": [\n" +
                "        {\n" +
                "            \"id\": \"Nwx635lhtm7\",\n" +
                "            \"name\": \"数据源\",\n" +
                "            \"type\": \"dataResource\",\n" +
                "            \"left\": \"181px\",\n" +
                "            \"top\": \"18px\",\n" +
                "            \"ico\": \"el-icon-setting\",\n" +
                "            \"state\": \"Kafka\",\n" +
                "            \"dataResourceDingyi\": \"\",\n" +
                "            \"dataResourceZhouqiState\": \"\",\n" +
                "            \"jdbc_DBUrl\": \"\",\n" +
                "            \"jdbc_userName\": \"\",\n" +
                "            \"jdbc_tableName\": \"\",\n" +
                "            \"jdbc_password\": \"\",\n" +
                "            \"jdbc_exConfig\": \"\",\n" +
                "            \"csv_path\": \"\",\n" +
                "            \"csv_exConfig\": \"\",\n" +
                "            \"redis_host\": \"\",\n" +
                "            \"redis_port\": \"\",\n" +
                "            \"redis_password\": \"\",\n" +
                "            \"redis_exConfig\": \"\",\n" +
                "            \"redis_command\": \"\",\n" +
                "            \"redis_key\": \"\",\n" +
                "            \"hdfs_host\": \"\",\n" +
                "            \"hdfs_port\": \"\",\n" +
                "            \"hdfs_filePath\": \"\",\n" +
                "            \"kafka_topic\": \"myTopic\",\n" +
                "            \"kafka_bootstrapServers\": \"192.168.10.102:9092\",\n" +
                "            \"kafka_groupId\": \"test-consumer-group\",\n" +
                "            \"Aggregation_tiaojian\": \"\",\n" +
                "            \"Aggregation_leixing\": \"\",\n" +
                "            \"Reduce_inLei\": \"\",\n" +
                "            \"Reduce_tiaojian\": \"\",\n" +
                "            \"Filter_inLei\": \"\",\n" +
                "            \"Filter_tiaojian\": \"\",\n" +
                "            \"Map_inLei\": \"\",\n" +
                "            \"Map_outLei\": \"\",\n" +
                "            \"Map_tiaojian\": \"\",\n" +
                "            \"FlatMap_inLei\": \"\",\n" +
                "            \"FlatMap_outLei\": \"\",\n" +
                "            \"FlatMap_tiaojian\": \"\",\n" +
                "            \"KeyBy_inLei\": \"\",\n" +
                "            \"KeyBy_outLei\": \"\",\n" +
                "            \"KeyBy_tiaojian\": \"\",\n" +
                "            \"dataResultType\": \"\",\n" +
                "            \"dataResultWindow_data\": \"\",\n" +
                "            \"dataResultMySql_url\": \"\",\n" +
                "            \"dataResultMySql_userName\": \"\",\n" +
                "            \"dataResultMySql_password\": \"\",\n" +
                "            \"dataResultMySql_tableName\": \"\",\n" +
                "            \"dataResultMySql_exConfig\": \"\",\n" +
                "            \"dataResultMySql_dataPeizhi\": \"\",\n" +
                "            \"dataResultCSV_path\": \"\",\n" +
                "            \"dataResultCSV_exConfig\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Nrnhnlknpiw\",\n" +
                "            \"name\": \"数据结果\",\n" +
                "            \"type\": \"dataResult\",\n" +
                "            \"left\": \"521px\",\n" +
                "            \"top\": \"224px\",\n" +
                "            \"ico\": \"el-icon-setting\",\n" +
                "            \"state\": \"\",\n" +
                "            \"dataResourceDingyi\": \"\",\n" +
                "            \"dataResourceZhouqiState\": \"\",\n" +
                "            \"jdbc_DBUrl\": \"\",\n" +
                "            \"jdbc_userName\": \"\",\n" +
                "            \"jdbc_tableName\": \"\",\n" +
                "            \"jdbc_password\": \"\",\n" +
                "            \"jdbc_exConfig\": \"\",\n" +
                "            \"csv_path\": \"\",\n" +
                "            \"csv_exConfig\": \"\",\n" +
                "            \"redis_host\": \"\",\n" +
                "            \"redis_port\": \"\",\n" +
                "            \"redis_password\": \"\",\n" +
                "            \"redis_exConfig\": \"\",\n" +
                "            \"redis_command\": \"\",\n" +
                "            \"redis_key\": \"\",\n" +
                "            \"hdfs_host\": \"\",\n" +
                "            \"hdfs_port\": \"\",\n" +
                "            \"hdfs_filePath\": \"\",\n" +
                "            \"kafka_topic\": \"\",\n" +
                "            \"kafka_bootstrapServers\": \"\",\n" +
                "            \"kafka_groupId\": \"\",\n" +
                "            \"Aggregation_tiaojian\": \"\",\n" +
                "            \"Aggregation_leixing\": \"\",\n" +
                "            \"Reduce_inLei\": \"\",\n" +
                "            \"Reduce_tiaojian\": \"\",\n" +
                "            \"Filter_inLei\": \"\",\n" +
                "            \"Filter_tiaojian\": \"\",\n" +
                "            \"Map_inLei\": \"\",\n" +
                "            \"Map_outLei\": \"\",\n" +
                "            \"Map_tiaojian\": \"\",\n" +
                "            \"FlatMap_inLei\": \"\",\n" +
                "            \"FlatMap_outLei\": \"\",\n" +
                "            \"FlatMap_tiaojian\": \"\",\n" +
                "            \"KeyBy_inLei\": \"\",\n" +
                "            \"KeyBy_outLei\": \"\",\n" +
                "            \"KeyBy_tiaojian\": \"\",\n" +
                "            \"dataResultType\": \"window\",\n" +
                "            \"dataResultWindow_data\": \"\",\n" +
                "            \"dataResultMySql_url\": \"\",\n" +
                "            \"dataResultMySql_userName\": \"\",\n" +
                "            \"dataResultMySql_password\": \"\",\n" +
                "            \"dataResultMySql_tableName\": \"\",\n" +
                "            \"dataResultMySql_exConfig\": \"\",\n" +
                "            \"dataResultMySql_dataPeizhi\": \"\",\n" +
                "            \"dataResultCSV_path\": \"\",\n" +
                "            \"dataResultCSV_exConfig\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Ne2n9hiz0k\",\n" +
                "            \"name\": \"数据源1\",\n" +
                "            \"type\": \"dataResource\",\n" +
                "            \"left\": \"112px\",\n" +
                "            \"top\": \"215.5px\",\n" +
                "            \"ico\": \"el-icon-setting\",\n" +
                "            \"state\": \"CSV\",\n" +
                "            \"dataResourceDingyi\": \"User\",\n" +
                "            \"dataResourceZhouqiState\": \"\",\n" +
                "            \"jdbc_DBUrl\": \"\",\n" +
                "            \"jdbc_userName\": \"\",\n" +
                "            \"jdbc_tableName\": \"\",\n" +
                "            \"jdbc_password\": \"\",\n" +
                "            \"jdbc_exConfig\": \"\",\n" +
                "            \"csv_path\": \"input/user.csv\",\n" +
                "            \"csv_exConfig\": \"\",\n" +
                "            \"redis_host\": \"\",\n" +
                "            \"redis_port\": \"\",\n" +
                "            \"redis_password\": \"\",\n" +
                "            \"redis_exConfig\": \"\",\n" +
                "            \"redis_command\": \"\",\n" +
                "            \"redis_key\": \"\",\n" +
                "            \"hdfs_host\": \"\",\n" +
                "            \"hdfs_port\": \"\",\n" +
                "            \"hdfs_filePath\": \"\",\n" +
                "            \"kafka_topic\": \"\",\n" +
                "            \"kafka_bootstrapServers\": \"\",\n" +
                "            \"kafka_groupId\": \"\",\n" +
                "            \"Aggregation_tiaojian\": \"\",\n" +
                "            \"Aggregation_leixing\": \"\",\n" +
                "            \"Reduce_inLei\": \"\",\n" +
                "            \"Reduce_tiaojian\": \"\",\n" +
                "            \"Filter_inLei\": \"\",\n" +
                "            \"Filter_tiaojian\": \"\",\n" +
                "            \"Map_inLei\": \"\",\n" +
                "            \"Map_outLei\": \"\",\n" +
                "            \"Map_tiaojian\": \"\",\n" +
                "            \"FlatMap_inLei\": \"\",\n" +
                "            \"FlatMap_outLei\": \"\",\n" +
                "            \"FlatMap_tiaojian\": \"\",\n" +
                "            \"KeyBy_inLei\": \"\",\n" +
                "            \"KeyBy_outLei\": \"\",\n" +
                "            \"KeyBy_tiaojian\": \"\",\n" +
                "            \"dataResultType\": \"\",\n" +
                "            \"dataResultWindow_data\": \"\",\n" +
                "            \"dataResultMySql_url\": \"\",\n" +
                "            \"dataResultMySql_userName\": \"\",\n" +
                "            \"dataResultMySql_password\": \"\",\n" +
                "            \"dataResultMySql_tableName\": \"\",\n" +
                "            \"dataResultMySql_exConfig\": \"\",\n" +
                "            \"dataResultMySql_dataPeizhi\": \"\",\n" +
                "            \"dataResultCSV_path\": \"\",\n" +
                "            \"dataResultCSV_exConfig\": \"\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"lineList\": [\n" +
                "        {\n" +
                "            \"from\": \"Ne2n9hiz0k\",\n" +
                "            \"to\": \"Nrnhnlknpiw\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        Utils.execute(entityJson,json);
    }
}
