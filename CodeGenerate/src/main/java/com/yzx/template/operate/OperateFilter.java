package com.yzx.template.operate;

import com.yzx.template.Node;

/**
 * 过滤节点
 */
public class OperateFilter extends Node {
    //过滤的条件语句
    public String condition;

    public OperateFilter() {
        javaPackage = "com.yzx.process";
        type = "Filter";
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
