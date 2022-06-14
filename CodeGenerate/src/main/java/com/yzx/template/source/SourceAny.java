package com.yzx.template.source;

import com.yzx.template.Node;

public class SourceAny extends Node {
    //用户自定义数据源的的入口类
    public String entryClass;

    public SourceAny() {
        type = "AnySource";
    }

    public String getEntryClass() {
        return entryClass;
    }

    public void setEntryClass(String entryClass) {
        this.entryClass = entryClass;
    }
}
