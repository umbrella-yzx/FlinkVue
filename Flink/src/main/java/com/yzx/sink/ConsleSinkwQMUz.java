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

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.http.Consts;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.flink.api.java.tuple.*;

public class ConsleSinkwQMUz extends RichSinkFunction<User> {

    private final String host = "http://192.168.10.1:9355/message";

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void invoke(User value, Context context) throws Exception {
        super.invoke(value, context);
        Map<String,String> map = new HashMap<>();
        map.put("JdbcToConsole",value.toString());
        sendPost(host,map);
    }

    @Override
    public void close() throws Exception {
    super.close();
    }

    public void sendPost(String url, Map<String,String> map) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        JSONObject jsonObject = new JSONObject();
        for(Map.Entry entry:map.entrySet()){
            jsonObject.put(entry.getKey().toString(),entry.getValue().toString());
        }   // 以上循环操作是将 Map对象转化成 JsonObject对象
        StringEntity entity = new StringEntity(jsonObject.toString(), Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);     //设置请求体
        try {
            httpclient.execute(httppost);
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
