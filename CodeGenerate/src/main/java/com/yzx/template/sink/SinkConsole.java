package com.yzx.template.sink;

import com.yzx.template.Node;

public class SinkConsole extends Node {

    public String JobName = "value";

    public String getJobName() {
        return JobName;
    }

    public void setJobName(String jobName) {
        JobName = jobName;
    }

    public SinkConsole() {
        javaPackage = "com.yzx.sink";
        type = "ConsleSink";
    }
}
