package com.yzx;

import com.yzx.controller.GraphJsonController;
import com.yzx.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class FlinkVueApplication {

    public static void main(String[] args) {
        //当前 Java 应用程序相关的运行时对象
        Runtime run=Runtime.getRuntime();
        //程序结束时删除所有生成的文件
        run.addShutdownHook(new Thread(Utils::deleteFile));

        GraphJsonController.keepOriginPom();

        SpringApplication.run(FlinkVueApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
//       SimpleClientHttpRequestFactory factory=new SimpleClientHttpRequestFactory();
//      上一行被注释掉的是Spring自己的实现，下面是依赖了httpclient包后的实现
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        return factory;
    }

    public static void startServer(){
        //为了简单起见，所有的异常信息都往外抛
        int port = 8800;
        try{
            //定义一个ServerSocket监听在端口8899上
            ServerSocket server = new ServerSocket(port);
            while (true) {
                //server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
                Socket socket = server.accept();
                //每接收到一个Socket就建立一个新的线程来处理它
                new Thread(new Task(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用来处理Socket请求的
     */
    static class Task implements Runnable {

        private Socket socket;

        public Task(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                handleSocket();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 跟客户端Socket进行通信
         * @throws Exception
         */
        private void handleSocket() throws Exception {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String temp;
            int index;
            while ((temp=br.readLine()) != null) {
                System.out.println(temp);
                if ((index = temp.indexOf("eof")) != -1) {//遇到eof时就结束接收
                    sb.append(temp.substring(0, index));
                    break;
                }
                sb.append(temp);
            }
            System.out.println("客户端: " + sb);
            //读完后写一句
            Writer writer = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            writer.write("你好，客户端。");
            writer.write("eof\n");
            writer.flush();
            writer.close();
            br.close();
            socket.close();
        }
    }
}
