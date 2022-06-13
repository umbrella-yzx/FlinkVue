package com.yzx.template.operate;

import com.yzx.template.Node;

/**
 * 扁平映射节点
 */
public class OperateFlatMap extends Node {
    //过滤的泛型类型
    public String inClass;
    //过滤的条件语句
    public String condition;

    public OperateFlatMap() {
        javaPackage = "com.yzx.process";
        type = "FlatMap";
    }

    public String getInClass() {
        return inClass;
    }

    public void setInClass(String inClass) {
        this.inClass = inClass;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
