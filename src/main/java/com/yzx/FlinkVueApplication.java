package com.yzx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FlinkVueApplication {

    public static void main(String[] args) {
//        //当前 Java 应用程序相关的运行时对象
//        Runtime run=Runtime.getRuntime();
//        //程序结束时删除所有生成的文件
//        run.addShutdownHook(new Thread(GraphJsonController::recoverPom));

        SpringApplication.run(FlinkVueApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
