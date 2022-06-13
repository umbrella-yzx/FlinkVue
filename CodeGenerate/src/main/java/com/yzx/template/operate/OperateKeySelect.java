package com.yzx.template.operate;

import com.yzx.template.Node;

/**
 * 分组字段选择器节点
 */
public class OperateKeySelect extends Node {
    //过滤的泛型类型
    public String inClass;
    //映射输出类型
    public String outClass;
    //选择器的条件语句
    public String condition;

    public OperateKeySelect() {
        javaPackage = "com.yzx.process";
        type = "KeySelect";
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
