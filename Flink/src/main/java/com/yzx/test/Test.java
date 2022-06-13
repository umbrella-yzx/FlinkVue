package com.yzx.test;


import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Test {
    public static void main(String[] args) throws Exception {
        String path = "E:\\Java\\Workspace\\FlinkVue\\Flink\\target\\Flink-1.0-SNAPSHOT-jar-with-dependencies.jar";
        loadJar(path);
        Class<?> aClass = Class.forName("com.yzx.process.ProcessWwyya");
        Method method=aClass.getDeclaredMethod("execute");
        method.invoke(null); //当类型为String[]时，需要(Object)new String[] {}初始化
    }

    public static void loadJar(String jarPath) {
        File jarFile = new File(jarPath);
        // 从URLClassLoader类中获取类所在文件夹的方法，jar也可以认为是一个文件夹
        Method method = null;
        try {
            method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        } catch (NoSuchMethodException | SecurityException e1) {
            e1.printStackTrace();
        }
        // 获取方法的访问权限以便写回
        boolean accessible = method.isAccessible();
        try {
            method.setAccessible(true);
            // 获取系统类加载器
            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            URL url = jarFile.toURI().toURL();
            method.invoke(classLoader, url);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.setAccessible(accessible);
        }
    }

//    public static String execte()throws Exception{
//        StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();
//
//        StringBuilder sb = new StringBuilder();
//
//        DataStreamSource<MyScores> stream = env.addSource(new SourceFunction<MyScores>() {
//            @Override
//            public void run(SourceContext<MyScores> sourceContext) throws Exception {
//                sourceContext.collect(new MyScores(1, "libai", 20, 50, 90));
//                sourceContext.collect(new MyScores(2, "tom", 80, 95, 52));
//                sourceContext.collect(new MyScores(3, "dsvf", 28, 75, 80));
//                sourceContext.collect(new MyScores(4, "jack", 49, 55, 62));
//            }
//
//            @Override
//            public void cancel() {
//
//            }
//        });
//
//
//
//        stream.addSink(new RichSinkFunction<MyScores>() {
//            @Override
//            public void open(Configuration parameters) throws Exception {
//                super.open(parameters);
//            }
//
//            @Override
//            public void invoke(MyScores value, Context context) throws Exception {
//                super.invoke(value, context);
//                try {
//                    //为了简单起见，所有的异常都直接往外抛
//                    String host = "127.0.0.1";  //要连接的服务端IP地址
//                    int port = 8800;   //要连接的服务端对应的监听端口
//                    //与服务端建立连接
//                    Socket client = new Socket(host, port);
//                    //建立连接后就可以往服务端写数据了
//                    Writer writer = new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8);
//                    writer.write(value.toString()+'\n');
//                    writer.flush();
//                    writer.close();
//                    client.close();
//            } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        env.execute();
//
//        return sb.toString();
//    }
}
