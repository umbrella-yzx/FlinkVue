package com.yzx.sink;

    import com.yzx.entity.User;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import org.apache.flink.api.java.tuple.*;

public class ConsleSinkJqtqA extends RichSinkFunction<User> {

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void invoke(User value, Context context) throws Exception {
        super.invoke(value, context);
        try {
            String host = "127.0.0.1";  //要连接的服务端IP地址
            int port = 8800;   //要连接的服务端对应的监听端口
            //与服务端建立连接
            Socket client = new Socket(host, port);
            //建立连接后就可以往服务端写数据了
            Writer writer = new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8);
            writer.write(value.toString()+'\n');
            writer.flush();
            writer.close();
            client.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
