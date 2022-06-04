package com.yzx.test.maintest;

import com.yzx.source.config.JdbcConfig;
import com.yzx.utils.ApplicationEnv;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Properties;

public class MyMain {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();
//
//        Properties properties = new Properties();
//
//        DataStreamSource<User> Nup31pednvd = env.addSource(new JdbcSourceXzBHL(new JdbcConfig("jdbc:mysql://localhost:3306/test", "user", "root", "root")));
//
//        SingleOutputStreamOperator<User> Nh7p035q0m = Nup31pednvd.filter(new FilteryMOhl());
//
//        Nh7p035q0m.addSink(new JdbcSinkwmKNZ(new JdbcConfig("jdbc:mysql://localhost:3306/test", "newuser", "root", "root")));
//
//        SingleOutputStreamOperator<Tuple2<Integer,String>> Nduajufskbn = Nh7p035q0m.map(new MapitdtG());
//
//        Nduajufskbn.writeAsCsv("input/myscores");

        env.execute();
    }

//    private static Entity generateEntity(){
//        Entity entity = new Entity();
//        entity.setJavaPackage("com.yzx.entity"); // 创建包名
//        entity.setClassName("User");  // 创建类名
//        entity.setConstructors(true); // 是否创建构造函数
//
//        List<Property> propertyList = new ArrayList<Property>();
//
//        // 创建实体属性一
//        Property attribute1 = new Property();
//        attribute1.setJavaType("String");
//        attribute1.setPropertyName("name");
//        attribute1.setPropertyType(PropertyType.String);
//
//        // 创建实体属性二
//        Property attribute2 = new Property();
//        attribute2.setJavaType("int");
//        attribute2.setPropertyName("age");
//        attribute2.setPropertyType(PropertyType.Int);
//
//        propertyList.add(attribute1);
//        propertyList.add(attribute2);
//
//        // 将属性集合添加到实体对象中
//        entity.setProperties(propertyList);
//        return entity;
//    }
//
//    private static SourceJdbc generateSourceJdbc(Entity entity){
//        SourceJdbc sourceJdbc = new SourceJdbc();
//        sourceJdbc.entity = entity;
//        sourceJdbc.curName = "sourcejdbc";
//        sourceJdbc.preName = "NULL";
//        sourceJdbc.jdbcConfig = new JdbcConfig("jdbc:mysql://localhost:3306/test", "user", "root", "root");
//        sourceJdbc.inPackages.add(entity.getJavaPackage()+"."+entity.getClassName());
//        return sourceJdbc;
//    }
//
//    private static OperateFilter generateOperateFilter(){
//        OperateFilter filter = new OperateFilter();
//        filter.className = "Filter1";
//        filter.curName = "filter1";
//        filter.outClass = "User";
//        filter.condition = "value.age>20";
//        filter.preName = "sourcejdbc";
//        filter.inPackages.add("com.yzx.entity.User");
//        return filter;
//    }
//
//    private static Process generateProcess(List<Node> nodes){
//        Process process = new Process();
//        process.nodes = nodes;
//        return process;
//    }
//
//    private static SourceCSV generateSourceCSV(Entity entity){
//        SourceCSV sourceCSV = new SourceCSV();
//        sourceCSV.entity = entity;
//        sourceCSV.sql = "CREATE TABLE mytable("+
//                " `id` INT not null," +
//                " `name` STRING not null," +
//                " `chinese` INT not null," +
//                " `english` INT not null," +
//                " `math` INT not null" +
//                ")WITH("+
//                " 'connector' = 'filesystem',"+
//                " 'path' = 'input/scores.csv',"+
//                " 'format' = 'csv'"+
//                ")";
//        sourceCSV.curName = "sourceCSV";
//        sourceCSV.preName = "NULL";
//        return sourceCSV;
//    }
}
