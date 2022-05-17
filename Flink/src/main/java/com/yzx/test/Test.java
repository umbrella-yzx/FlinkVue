package com.yzx.test;

import com.yzx.connector.csv.CSVConnector;
import com.yzx.connector.jdbc.JdbcConnector;
import com.yzx.connector.config.CSVConfig;
import com.yzx.connector.config.JdbcConfig;
import com.yzx.process.FilterFunction;
import com.yzx.process.GroupFunction;
import com.yzx.sink.ConsoleSink;
import com.yzx.utils.ApplicationEnv;
import com.yzx.utils.UDF;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class Test {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();
        StreamTableEnvironment tableEnvironment = ApplicationEnv.getTableEnvironment();

        UDF.createUDF("ScoresAvg","file:input/ScoresAvg.jar","com.acc.ScoresAvg");

        long startTime = System.currentTimeMillis();    //获取开始时间

        JdbcConnector jdbcConnector = new JdbcConnector(new JdbcConfig("jdbc:mysql://localhost:3306/test", "student", "root", "root"));
        CSVConnector csvConnector = new CSVConnector(new CSVConfig("input/scores.csv"));
        TableResult studentTable = jdbcConnector.connect("CREATE TABLE student(" +
                " `id` INT not null," +
                " `name` STRING not null," +
                " `class` INT not null," +
                " `gender` STRING not null" +
                ")");


        TableResult scoresTable = csvConnector.connect("CREATE TABLE scores(" +
                " `id` INT not null," +
                " `name` STRING not null," +
                " `Chinese` INT not null," +
                " `English` INT not null," +
                " `Math` INT not null" +
                ")");

        FilterFunction filterFunction = new FilterFunction();
        Table joinTable = filterFunction.sqlQuery("select scores.id,student.class,Chinese, English,Math from scores inner join student on scores.id=student.id");

        GroupFunction groupFunction = new GroupFunction();
        TableResult outputTable = groupFunction.executeSql("select class,ScoresAvg(Chinese) as AvgChinese,ScoresAvg(English) as AvgEnglish,ScoresAvg(Math) as AvgMath from "+joinTable+" group by class");

        new ConsoleSink().print(outputTable);

        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println("处理时间为：" + (endTime - startTime) + "ms");    //输出程序运行时间
    }
}
