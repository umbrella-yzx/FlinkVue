package com.yzx.template.operate;

import com.yzx.template.Node;

/**
 * 聚合节点
 */
public class OperateReduce  extends Node {
    //reduce的条件语句
    public String condition;

    public OperateReduce() {
        javaPackage = "com.yzx.process";
        type = "Reduce";
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
