package com.yzx.template.sink;

import com.yzx.template.Node;

import java.net.InetAddress;

public class SinkConsole extends Node {

    public String JobName = "value";

    //项目运行所在机器的ip地址
    public String ipAddr;

    public String getJobName() {
        return JobName;
    }

    public void setJobName(String jobName) {
        JobName = jobName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public SinkConsole() {
        javaPackage = "com.yzx.sink";
        type = "ConsleSink";

        try {
            InetAddress addr = InetAddress.getLocalHost();
            ipAddr = addr.getHostAddress();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
