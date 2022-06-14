package com.yzx;

import com.alibaba.fastjson.JSONObject;
import com.yzx.dao.JdbcConfigDao;
import com.yzx.domain.JdbcConfig;
import com.yzx.service.IJdbcConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class FlinkVueApplicationTests {

    String flinkUrl = "http://192.168.10.102:8081";

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void test(){
        String str = "";
//        str = restTemplate.getForObject(flinkUrl+"/jobs/overview",String.class);
//        System.out.println(str);
//        str = restTemplate.getForObject(flinkUrl+"/jobs/09f524fdb70fa59898c8500878baba84/metrics",String.class);
//        System.out.println(str);
        str = restTemplate.getForObject(flinkUrl+"/jars",String.class);
        System.out.println(str);


//
        JSONObject msg = new JSONObject();
        msg.put("target-directory","/tmp");
        msg.put("cancel-job",true);
//        msg.put("key", "@*2y9$jl");
//        msg.put("receiver", receiverEcommerceId);
//        msg.put("title", title);
//        msg.put("content", content);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
//        restTemplate.postForObject( "http://192.168.10.102:8081/jobs/0ec90dad367c8e1f0b27a3d096f1c5b4/savepoints",msg,JSONObject.class);
//        System.out.println(str);


//        str = "";
//        restTemplate.patchForObject( "http://192.168.10.102:8081/jobs/be445856052b8d1dada1ec85b5c7da6b",str,String.class);
//        System.out.println(str);
//
//        msg.clear();
//        msg.put("targetDirectory","/tmp");
//        restTemplate.postForObject( "http://192.168.10.102:8081/jobs/0ec90dad367c8e1f0b27a3d096f1c5b4/stop",msg,JSONObject.class);
//        System.out.println(str);

//        msg.clear();
////        msg.put("savepointPath","/tmp");
//        restTemplate.postForObject( "http://192.168.10.102:8081/jars/d59d0a74-56df-47a7-899b-2e73e96ea07c_Flink-1.0-SNAPSHOT-jar-with-dependencies.jar/run",msg,JSONObject.class);
//        System.out.println(str);

    }
}
