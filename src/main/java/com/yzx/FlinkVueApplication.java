package com.yzx;

import com.yzx.controller.WindowDataController;
import com.yzx.data.SharedData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class FlinkVueApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlinkVueApplication.class, args);
        getConsoleMessage();
    }

    static ServerSocket server;

    static {
        try {
            server = new ServerSocket(8800);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Boolean isRunning = true;

    public static void getConsoleMessage(){
        isRunning = true;

        try {
            String res = null;
            while (isRunning) {
                //server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
                Socket socket = server.accept();
                //每接收到一个Socket就建立一个新的线程来处理它
                Task task = new Task(socket);
                Thread thread = new Thread(task);
                thread.start();
                thread.join();
                res = task.getData();
                SharedData.sb.append(res);
            }
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * 用来处理Socket请求的
     */
    static class Task implements Runnable {

        private String data = "";

        private Socket socket;

        public String getData() {
            return data;
        }

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
            //获得输出流
            OutputStream os=socket.getOutputStream();
            //4.读取用户输入信息
            StringBuilder res = new StringBuilder();
            String info;
            while(!((info=br.readLine())==null)){
//                System.out.println(info);
                res.append(info);
            }
            br.close();
            socket.close();
            data =  res.toString();
        }
    }
}
