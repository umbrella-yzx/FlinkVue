package com.yzx.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Test {
    public static void main(String[] args) throws Exception {
//        String path = "E:\\Java\\Workspace\\FlinkVue\\Flink\\target\\Flink-1.0-SNAPSHOT-jar-with-dependencies.jar";
//        Utils.loadJar(path);
//        Class<?> aClass = Class.forName("com.yzx.process.ProcessWwyya");
//        Method method=aClass.getDeclaredMethod("execute");
//        method.invoke(null); //当类型为String[]时，需要(Object)new String[] {}初始化
//        String[] cmds={"curl","-X", "POST",  "--header", "\"Except:\"","-F",
//                "\"jarfile=@E:\\Java\\Workspace\\FlinkVue\\Flink\\target\\Flink-1.0-SNAPSHOT-jar-with-dependencies.jar\""
//                ,"http://192.168.10.102:8081/jars/upload"};//必须分开写，不能有空格
//        String s = execCurl(cmds);
//        String path = "/jars/c6279a49-ae26-4a55-a5d3-780ffcddb01b_Flink-1.0-SNAPSHOT-jar-with-dependencies.jar/run";
//        String param = "?entry-class=com.yzx.process.ProcessPMkOZ";
//        restCallerPost(path,param);
        List<String> str = new ArrayList<>();
    }

    public static String execCurl() {
        String[] cmds={"curl","-X", "POST",  "--header", "\"Except:\"","-F",
                "\"jarfile=@E:\\Java\\Workspace\\FlinkVue\\Flink\\target\\Flink-1.0-SNAPSHOT-jar-with-dependencies.jar\""
                ,"http://192.168.10.102:8081/jars/upload"};//必须分开写，不能有空格

        ProcessBuilder process = new ProcessBuilder(cmds);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();

        } catch (IOException e) {
            System.out.print("error");
            e.printStackTrace();
        }
        return null;

    }

}
