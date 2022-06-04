package com.yzx.test;

import com.yzx.source.config.KafkaConfig;
import com.yzx.utils.ApplicationEnv;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class KafkaTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();

        KafkaConfig kafkaConfig = new KafkaConfig();

        Properties properties = new Properties();
        properties.clear();

        kafkaConfig.setTopic("myTopic");
        List<KafkaConfig.MyProperty> list = new ArrayList<>();
        list.add(new KafkaConfig.MyProperty("bootstrap.servers","192.168.10.102:9092"));
        list.add(new KafkaConfig.MyProperty("group.id","test-consumer-group"));
        kafkaConfig.setProperties(list);

        for(KafkaConfig.MyProperty myProperty:list){
            properties.setProperty(myProperty.key,myProperty.value);
        }

        DataStream<String> stream = env
                .addSource(new FlinkKafkaConsumer<String>(kafkaConfig.getTopic(), new SimpleStringSchema(), properties));

        stream.print();

        env.execute();
    }


}
