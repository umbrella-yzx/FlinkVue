package com.yzx.test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Test {
    public static void main(String[] args) throws Exception {
        //获取项目路径
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        System.out.println("path2: "+courseFile);
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
