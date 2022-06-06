package com.yzx.template.source;

import com.yzx.source.config.HDFSConfig;
import com.yzx.test.Node;

public class SourceHDFS extends Node {

    public HDFSConfig hdfsConfig;

    //类型为String不用外部赋值
    public String outClass;

    public SourceHDFS() {
        javaPackage = "com.yzx.source";
        className = "HDFSSource";
        type = "HDFSSource";
        outClass = "String";
    }

    public HDFSConfig getHdfsConfig() {
        return hdfsConfig;
    }

    public void setHdfsConfig(HDFSConfig hdfsConfig) {
        this.hdfsConfig = hdfsConfig;
    }

    public String getOutClass() {
        return outClass;
    }

    public void setOutClass(String outClass) {
        this.outClass = outClass;
    }
}
