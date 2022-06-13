package com.yzx;

import com.yzx.dao.JdbcConfigDao;
import com.yzx.domain.JdbcConfig;
import com.yzx.service.IJdbcConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class FlinkVueApplicationTests {

    String flinkUrl = "http://192.168.10.102:8081";

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void test(){
        String str = "";
        str = restTemplate.getForObject(flinkUrl+"/jobs/overview",String.class);
        System.out.println(str);
//        str = restTemplate.getForObject(flinkUrl+"/jobs/09f524fdb70fa59898c8500878baba84/metrics",String.class);
//        System.out.println(str);
//        str = restTemplate.getForObject(flinkUrl+"/jars",String.class);
//        System.out.println(str);

        restTemplate.patchForObject( "http://192.168.10.102:8081/jobs/09f524fdb70fa59898c8500878baba84",str,String.class);
        System.out.println(str);

    }
}
