package com.yzx.utils;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UDF {
    private static StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();
    private static StreamTableEnvironment tableEnv = ApplicationEnv.getTableEnvironment();

    public static void createUDF(String funName, String filePath, String classPath) throws Exception {
        loadJar(new URL(filePath));

        Field configuration = StreamExecutionEnvironment.class.getDeclaredField("configuration");
        configuration.setAccessible(true);
        Configuration o = (Configuration)configuration.get(env);

        Field confData = Configuration.class.getDeclaredField("confData");
        confData.setAccessible(true);
        Map<String,Object> temp = (Map<String,Object>)confData.get(o);
        List<String> jarList = new ArrayList<>();
        jarList.add(filePath);
        temp.put("pipeline.classpaths",jarList);
        tableEnv.executeSql("CREATE FUNCTION "+funName+" AS '"+classPath+"'");
    }

    //动态加载Jar
    public static void loadJar(URL jarUrl) {
        //从URLClassLoader类加载器中获取类的addURL方法
        Method method = null;
        try {
            method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        } catch (NoSuchMethodException | SecurityException e1) {
            e1.printStackTrace();
        }
        // 获取方法的访问权限
        boolean accessible = method.isAccessible();

        try {
            //修改访问权限为可写
            if (accessible == false) {
                method.setAccessible(true);
            }
            // 获取系统类加载器
            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            //jar路径加入到系统url路径里
            method.invoke(classLoader, jarUrl);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.setAccessible(accessible);
        }
    }
}
