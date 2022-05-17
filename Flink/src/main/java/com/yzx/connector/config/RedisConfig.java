package com.yzx.connector.config;

public class RedisConfig {
    private String host;
    private int port;
    private String password;

    public RedisConfig() {
    }

    public RedisConfig(String host, int port, String password) {
        this.host = host;
        this.port = port;
        this.password = password;
    }

    public RedisConfig(String host) {
        this.host = host;
        this.port = 6379;
        this.password = null;
    }

    public RedisConfig(String host, int port) {
        this.host = host;
        this.port = port;
        this.password = null;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RedisConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", password='" + password + '\'' +
                '}';
    }
}
