package com.yzx.template;

import java.util.ArrayList;
import java.util.List;

public class Process {
    // 实体所在的包名
    public String javaPackage = "com.yzx.process";
    // 实体类名
    public String className = "Process";

    public List<Node> nodes = new ArrayList<>();

    public String getJavaPackage() {
        return javaPackage;
    }

    public void setJavaPackage(String javaPackage) {
        this.javaPackage = javaPackage;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
