package com.yzx.template.operate;

import com.yzx.test.Node;

/**
 * 聚合节点
 */
public class OperateReduce  extends Node {
    //reduce的泛型类型
    public String outClass;
    //reduce的条件语句
    public String condition;

    public OperateReduce() {
        javaPackage = "com.yzx.process";
        type = "Reduce";
    }

    public String getOutClass() {
        return outClass;
    }

    public void setOutClass(String outClass) {
        this.outClass = outClass;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
