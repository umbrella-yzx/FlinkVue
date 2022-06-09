package com.yzx.template.source;

import com.yzx.source.config.HDFSConfig;
import com.yzx.template.Node;

public class SourceHDFS extends Node {

    public HDFSConfig hdfsConfig;

    public SourceHDFS() {
//        javaPackage = "com.yzx.source";
//        className = "HDFSSource";
        type = "HDFSSource";
        outClass = "String";//类型为String不用外部赋值
    }

    public HDFSConfig getHdfsConfig() {
        return hdfsConfig;
    }

    public void setHdfsConfig(HDFSConfig hdfsConfig) {
        this.hdfsConfig = hdfsConfig;
    }
}
