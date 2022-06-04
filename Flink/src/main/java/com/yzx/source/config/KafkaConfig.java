package com.yzx.source.config;

import java.util.List;
import java.util.Properties;

public class KafkaConfig {
    private String topic;
    private List<MyProperty> properties;

    public List<MyProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<MyProperty> properties) {
        this.properties = properties;
    }

    public KafkaConfig() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public static class MyProperty{
        public String key;
        public String value;

        public MyProperty() {
        }

        public MyProperty(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
