package com.yzx.test.maintest;


import com.yzx.source.config.JdbcConfig;
import com.yzx.template.Node;
import com.yzx.template.Process;
import com.yzx.template.operate.OperateFilter;
import com.yzx.template.source.SourceCSV;
import com.yzx.template.source.SourceJdbc;
import com.yzx.utils.Utils;
import com.yzx.template.entity.Entity;
import com.yzx.template.entity.Property;
import com.yzx.template.entity.PropertyType;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

public class MyMain {

    public static void main(String[] args) throws Exception {
        //当前 Java 应用程序相关的运行时对象
        Runtime run=Runtime.getRuntime();
        //程序结束时删除所有生成的文件
        run.addShutdownHook(new Thread(Utils::deleteFile));

        List<Node> nodes = new ArrayList<>();

        Entity entity = generateEntity();
        SourceJdbc sourceJdbc = generateSourceJdbc(entity);
        OperateFilter filter = generateOperateFilter();
        nodes.add(sourceJdbc);
        nodes.add(filter);
        Process process = generateProcess(nodes);
        Utils.generateCode(entity,0,"entity.ftl");
        Utils.generateCode(sourceJdbc,1,"source_jdbc.ftl");
        Utils.generateCode(filter,2,"operator_filter.ftl");
        Utils.generateCode(process,4,"process.ftl");

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

    private static Entity generateEntity(){
        Entity entity = new Entity();
        entity.setJavaPackage("com.yzx.entity"); // 创建包名
        entity.setClassName("User");  // 创建类名
        entity.setConstructors(true); // 是否创建构造函数

        List<Property> propertyList = new ArrayList<Property>();

        // 创建实体属性一
        Property attribute1 = new Property();
        attribute1.setJavaType("String");
        attribute1.setPropertyName("name");
        attribute1.setPropertyType(PropertyType.String);

        // 创建实体属性二
        Property attribute2 = new Property();
        attribute2.setJavaType("int");
        attribute2.setPropertyName("age");
        attribute2.setPropertyType(PropertyType.Int);

        propertyList.add(attribute1);
        propertyList.add(attribute2);

        // 将属性集合添加到实体对象中
        entity.setProperties(propertyList);
        return entity;
    }

    private static SourceJdbc generateSourceJdbc(Entity entity){
        SourceJdbc sourceJdbc = new SourceJdbc();
        sourceJdbc.entity = entity;
        sourceJdbc.curName = "sourcejdbc";
        sourceJdbc.preName = "NULL";
        sourceJdbc.jdbcConfig = new JdbcConfig("jdbc:mysql://localhost:3306/test", "user", "root", "root");
        sourceJdbc.inPackages.add(entity.getJavaPackage()+"."+entity.getClassName());
        return sourceJdbc;
    }

    private static OperateFilter generateOperateFilter(){
        OperateFilter filter = new OperateFilter();
        filter.className = "Filter1";
        filter.curName = "filter1";
        filter.inClass = "User";
        filter.condition = "value.age>20";
        filter.preName = "sourcejdbc";
        filter.inPackages.add("com.yzx.entity.User");
        return filter;
    }

    private static Process generateProcess(List<Node> nodes){
        Process process = new Process();
        process.nodes = nodes;
        return process;
    }

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
//    }
}
