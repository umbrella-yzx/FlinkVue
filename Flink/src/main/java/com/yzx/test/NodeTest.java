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
                "            \"id\": \"Nup31pednvd\",\n" +
                "            \"name\": \"数据源\",\n" +
                "            \"type\": \"dataResource\",\n" +
                "            \"left\": \"157px\",\n" +
                "            \"top\": \"149.5px\",\n" +
                "            \"ico\": \"el-icon-setting\",\n" +
                "            \"state\": \"JDBC\",\n" +
                "            \"dataResourceDingyi\": \"User\",\n" +
                "            \"dataResourceZhouqiState\": \"\",\n" +
                "            \"jdbc_DBUrl\": \"jdbc:mysql://localhost:3306/test\",\n" +
                "            \"jdbc_userName\": \"root\",\n" +
                "            \"jdbc_tableName\": \"user\",\n" +
                "            \"jdbc_password\": \"root\",\n" +
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
                "            \"id\": \"Nh7p035q0m\",\n" +
                "            \"name\": \"过滤\",\n" +
                "            \"type\": \"Filter\",\n" +
                "            \"left\": \"333px\",\n" +
                "            \"top\": \"337px\",\n" +
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
                "            \"Filter_inLei\": \"User\",\n" +
                "            \"Filter_tiaojian\": \"value.age>20\",\n" +
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
                "            \"id\": \"N5rtwqafk\",\n" +
                "            \"name\": \"数据结果\",\n" +
                "            \"type\": \"dataResult\",\n" +
                "            \"left\": \"594px\",\n" +
                "            \"top\": \"474px\",\n" +
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
                "            \"dataResultType\": \"MySql\",\n" +
                "            \"dataResultWindow_data\": \"\",\n" +
                "            \"dataResultMySql_url\": \"jdbc:mysql://localhost:3306/test\",\n" +
                "            \"dataResultMySql_userName\": \"root\",\n" +
                "            \"dataResultMySql_password\": \"root\",\n" +
                "            \"dataResultMySql_tableName\": \"newuser\",\n" +
                "            \"dataResultMySql_exConfig\": \"\",\n" +
                "            \"dataResultMySql_dataPeizhi\": \"User\",\n" +
                "            \"dataResultCSV_path\": \"\",\n" +
                "            \"dataResultCSV_exConfig\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"Nduajufskbn\",\n" +
                "            \"name\": \"映射\",\n" +
                "            \"type\": \"Map\",\n" +
                "            \"left\": \"587px\",\n" +
                "            \"top\": \"156px\",\n" +
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
                "            \"Map_inLei\": \"User\",\n" +
                "            \"Map_outLei\": \"Tuple2<Integer,String>\",\n" +
                "            \"Map_tiaojian\": \"new Tuple2<Integer,String>(value.age,value.name)\",\n" +
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
                "            \"id\": \"N10950i0su4\",\n" +
                "            \"name\": \"数据结果1\",\n" +
                "            \"type\": \"dataResult\",\n" +
                "            \"left\": \"807.2px\",\n" +
                "            \"top\": \"265.9px\",\n" +
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
                "            \"dataResultType\": \"CSV\",\n" +
                "            \"dataResultWindow_data\": \"\",\n" +
                "            \"dataResultMySql_url\": \"\",\n" +
                "            \"dataResultMySql_userName\": \"\",\n" +
                "            \"dataResultMySql_password\": \"\",\n" +
                "            \"dataResultMySql_tableName\": \"\",\n" +
                "            \"dataResultMySql_exConfig\": \"\",\n" +
                "            \"dataResultMySql_dataPeizhi\": \"\",\n" +
                "            \"dataResultCSV_path\": \"F:input/newuser\",\n" +
                "            \"dataResultCSV_outLei\":\"Tuple2<Integer,String>\",           \n" +
                "            \"dataResultCSV_exConfig\": \"\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"lineList\": [\n" +
                "        {\n" +
                "            \"from\": \"Nup31pednvd\",\n" +
                "            \"to\": \"Nh7p035q0m\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"from\": \"Nh7p035q0m\",\n" +
                "            \"to\": \"Nduajufskbn\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"from\": \"Nh7p035q0m\",\n" +
                "            \"to\": \"N5rtwqafk\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"from\": \"Nduajufskbn\",\n" +
                "            \"to\": \"N10950i0su4\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //当前 Java 应用程序相关的运行时对象
        Runtime run=Runtime.getRuntime();
        //程序结束时删除所有生成的文件
        run.addShutdownHook(new Thread(Utils::deleteFile));

        Utils.generateEntity(entityJson);

        Utils.generateProcess(json);

        //通过反射方法动态执行
        //1、首先构建文件的目录url地址，
        URL[] urls =new URL[] {new URL("file:E:/Java/Workspace/FlinkVue/Flink/src/main/java/com/yzx/test/maintest/")};
        //2、使用URLClassLoader对象的loadClass方法加载对应类
        URLClassLoader loder=new URLClassLoader(urls);
        //3、获取所加载类的方法
        Class<?> clazz =loder.loadClass("com.yzx.test.maintest.Process");
        // 4、传入方法所需的参数通过invoke运行方法
        Method method=clazz.getDeclaredMethod("execute");
        method.invoke(null); //当类型为String[]时，需要(Object)new String[] {}初始化
    }
}
