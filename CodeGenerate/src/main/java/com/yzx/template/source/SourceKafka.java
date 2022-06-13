package com.yzx.template.source;

import com.yzx.source.config.KafkaConfig;
import com.yzx.template.Node;

public class SourceKafka extends Node {
    public KafkaConfig kafkaConfig;

    public SourceKafka() {
//        javaPackage = "com.yzx.source";
//        className = "KafkaSource";
        type = "KafkaSource";
        outClass = "String";
    }

    public KafkaConfig getKafkaConfig() {
        return kafkaConfig;
    }

    public void setKafkaConfig(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }
}
