package com.yzx.template.operate;

import com.yzx.template.Node;

/**
 * 数据量合并操作节点
 * DataStream* → DataStream
 */
public class OperateUnion extends Node {
    //聚合的数据流名称
    public String condition;

    public OperateUnion() {
        type = "Union";
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
