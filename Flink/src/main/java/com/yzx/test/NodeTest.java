//package com.yzx.test;
//
//import com.yzx.utils.Utils;
//
//public class NodeTest {
//    public static void main(String[] args) throws Exception {
//        String entityJson = "[\n" +
//                "    {\n" +
//                "        \"name\": \"User\",\n" +
//                "        \"ziduan\": [\n" +
//                "            {\n" +
//                "                \"name\": \"name\",\n" +
//                "                \"type\": \"String\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"name\": \"age\",\n" +
//                "                \"type\": \"Int\"\n" +
//                "            }\n" +
//                "        ]\n" +
//                "    }\n" +
//                "]";
//
//
//
//        String json = "{\n" +
//                "    \"name\": \"默认\",\n" +
//                "    \"nodeList\": [\n" +
//                "        {\n" +
//                "            \"id\": \"Nd1lkjpoht\",\n" +
//                "            \"name\": \"数据源\",\n" +
//                "            \"type\": \"dataResource\",\n" +
//                "            \"left\": \"110px\",\n" +
//                "            \"top\": \"139px\",\n" +
//                "            \"ico\": \"el-icon-setting\",\n" +
//                "            \"state\": \"JDBC\",\n" +
//                "            \"dataResourceDingyi\": \"User\",\n" +
//                "            \"jdbc_DBUrl\": \"jdbc:mysql://192.168.10.1:3306/test\",\n" +
//                "            \"jdbc_userName\": \"root\",\n" +
//                "            \"jdbc_tableName\": \"user\",\n" +
//                "            \"jdbc_password\": \"root\",\n" +
//                "            \"jdbc_exConfig\": null,\n" +
//                "            \"field_id\": 1\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"id\": \"N9yle8lznie\",\n" +
//                "            \"name\": \"过滤\",\n" +
//                "            \"type\": \"Filter\",\n" +
//                "            \"left\": \"312px\",\n" +
//                "            \"top\": \"318.5px\",\n" +
//                "            \"ico\": \"el-icon-setting\",\n" +
//                "            \"state\": \"\",\n" +
//                "            \"dataResourceDingyi\": \"\",\n" +
//                "            \"dataResourceZhouqiState\": \"\",\n" +
//                "            \"jdbc_DBUrl\": \"\",\n" +
//                "            \"jdbc_userName\": \"\",\n" +
//                "            \"jdbc_tableName\": \"\",\n" +
//                "            \"jdbc_password\": \"\",\n" +
//                "            \"jdbc_exConfig\": \"\",\n" +
//                "            \"csv_path\": \"\",\n" +
//                "            \"csv_exConfig\": \"\",\n" +
//                "            \"redis_host\": \"\",\n" +
//                "            \"redis_port\": \"\",\n" +
//                "            \"redis_password\": \"\",\n" +
//                "            \"redis_exConfig\": \"\",\n" +
//                "            \"redis_command\": \"\",\n" +
//                "            \"redis_key\": \"\",\n" +
//                "            \"hdfs_host\": \"\",\n" +
//                "            \"hdfs_port\": \"\",\n" +
//                "            \"hdfs_filePath\": \"\",\n" +
//                "            \"kafka_topic\": \"\",\n" +
//                "            \"kafka_bootstrapServers\": \"\",\n" +
//                "            \"kafka_groupId\": \"\",\n" +
//                "            \"Aggregation_tiaojian\": \"\",\n" +
//                "            \"Aggregation_leixing\": \"\",\n" +
//                "            \"Reduce_inLei\": \"\",\n" +
//                "            \"Reduce_tiaojian\": \"\",\n" +
//                "            \"Filter_inLei\": \"\",\n" +
//                "            \"Filter_tiaojian\": \"value.age>20\",\n" +
//                "            \"Map_inLei\": \"\",\n" +
//                "            \"Map_outLei\": \"\",\n" +
//                "            \"Map_tiaojian\": \"\",\n" +
//                "            \"FlatMap_inLei\": \"\",\n" +
//                "            \"FlatMap_outLei\": \"\",\n" +
//                "            \"FlatMap_tiaojian\": \"\",\n" +
//                "            \"KeyBy_inLei\": \"\",\n" +
//                "            \"KeyBy_outLei\": \"\",\n" +
//                "            \"KeyBy_tiaojian\": \"\",\n" +
//                "            \"dataResultType\": \"\",\n" +
//                "            \"dataResultWindow_data\": \"\",\n" +
//                "            \"dataResultMySql_url\": \"\",\n" +
//                "            \"dataResultMySql_userName\": \"\",\n" +
//                "            \"dataResultMySql_password\": \"\",\n" +
//                "            \"dataResultMySql_tableName\": \"\",\n" +
//                "            \"dataResultMySql_exConfig\": \"\",\n" +
//                "            \"dataResultMySql_dataPeizhi\": \"\",\n" +
//                "            \"dataResultCSV_path\": \"\",\n" +
//                "            \"dataResultCSV_exConfig\": \"\",\n" +
//                "            \"dataResultCSV_outLei\": \"\",\n" +
//                "            \"field_id\": \"\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"id\": \"Nmp3echg9l\",\n" +
//                "            \"name\": \"数据结果\",\n" +
//                "            \"type\": \"dataResult\",\n" +
//                "            \"left\": \"649px\",\n" +
//                "            \"top\": \"135px\",\n" +
//                "            \"ico\": \"el-icon-setting\",\n" +
//                "            \"state\": \"\",\n" +
//                "            \"dataResourceDingyi\": \"\",\n" +
//                "            \"dataResourceZhouqiState\": \"\",\n" +
//                "            \"jdbc_DBUrl\": \"\",\n" +
//                "            \"jdbc_userName\": \"\",\n" +
//                "            \"jdbc_tableName\": \"\",\n" +
//                "            \"jdbc_password\": \"\",\n" +
//                "            \"jdbc_exConfig\": \"\",\n" +
//                "            \"csv_path\": \"\",\n" +
//                "            \"csv_exConfig\": \"\",\n" +
//                "            \"redis_host\": \"\",\n" +
//                "            \"redis_port\": \"\",\n" +
//                "            \"redis_password\": \"\",\n" +
//                "            \"redis_exConfig\": \"\",\n" +
//                "            \"redis_command\": \"\",\n" +
//                "            \"redis_key\": \"\",\n" +
//                "            \"hdfs_host\": \"\",\n" +
//                "            \"hdfs_port\": \"\",\n" +
//                "            \"hdfs_filePath\": \"\",\n" +
//                "            \"kafka_topic\": \"\",\n" +
//                "            \"kafka_bootstrapServers\": \"\",\n" +
//                "            \"kafka_groupId\": \"\",\n" +
//                "            \"Aggregation_tiaojian\": \"\",\n" +
//                "            \"Aggregation_leixing\": \"\",\n" +
//                "            \"Reduce_inLei\": \"\",\n" +
//                "            \"Reduce_tiaojian\": \"\",\n" +
//                "            \"Filter_inLei\": \"\",\n" +
//                "            \"Filter_tiaojian\": \"\",\n" +
//                "            \"Map_inLei\": \"\",\n" +
//                "            \"Map_outLei\": \"\",\n" +
//                "            \"Map_tiaojian\": \"\",\n" +
//                "            \"FlatMap_inLei\": \"\",\n" +
//                "            \"FlatMap_outLei\": \"\",\n" +
//                "            \"FlatMap_tiaojian\": \"\",\n" +
//                "            \"KeyBy_inLei\": \"\",\n" +
//                "            \"KeyBy_outLei\": \"\",\n" +
//                "            \"KeyBy_tiaojian\": \"\",\n" +
//                "            \"dataResultType\": \"window\",\n" +
//                "            \"dataResultWindow_data\": \"\",\n" +
//                "            \"dataResultMySql_url\": \"\",\n" +
//                "            \"dataResultMySql_userName\": \"\",\n" +
//                "            \"dataResultMySql_password\": \"\",\n" +
//                "            \"dataResultMySql_tableName\": \"\",\n" +
//                "            \"dataResultMySql_exConfig\": \"\",\n" +
//                "            \"dataResultMySql_dataPeizhi\": \"\",\n" +
//                "            \"dataResultCSV_path\": \"\",\n" +
//                "            \"dataResultCSV_exConfig\": \"\",\n" +
//                "            \"dataResultCSV_outLei\": \"\",\n" +
//                "            \"field_id\": \"\"\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"lineList\": [\n" +
//                "        {\n" +
//                "            \"from\": \"Nd1lkjpoht\",\n" +
//                "            \"to\": \"N9yle8lznie\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"from\": \"N9yle8lznie\",\n" +
//                "            \"to\": \"Nmp3echg9l\"\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
//
//        Utils.execute(entityJson,json);
//    }
//}
