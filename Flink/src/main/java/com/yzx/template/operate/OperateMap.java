package com.yzx.template.operate;

import com.yzx.template.Node;

/**
 * 映射节点
 */
public class OperateMap extends Node {
    //映射输入类型
    public String inClass;
    //映射输出类型
    public String outClass;
    //映射条件语句
    public String condition;

    public OperateMap() {
        javaPackage = "com.yzx.process";
        type = "Map";
    }

    public String getInClass() {
        return inClass;
    }

    public void setInClass(String inClass) {
        this.inClass = inClass;
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
