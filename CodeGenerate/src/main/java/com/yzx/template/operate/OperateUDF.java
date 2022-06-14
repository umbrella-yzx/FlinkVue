package com.yzx.template.operate;

import com.yzx.template.Node;

public class OperateUDF extends Node {
    //用户自定义数据处理的的入口类
    public String entryClass;
    //用户数据处理类型(如：filter等）
    public String operateType;

    public OperateUDF() {
        type = "UDF";
    }

    public String getEntryClass() {
        return entryClass;
    }

    public void setEntryClass(String entryClass) {
        this.entryClass = entryClass;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
}
