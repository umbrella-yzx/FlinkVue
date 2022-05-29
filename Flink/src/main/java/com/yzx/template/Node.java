package com.yzx.template;

import java.util.ArrayList;
import java.util.List;

public class Node {
    //算子类型
    public String type;
    //前节点标识
    public String preName = "";
    //后节点标识
    public String nxtName = "";
    //当前节点标识
    public String curName = "";
    // 实体所在的包名
    public String javaPackage;
    // 实体类名
    public String className;
    //需要导入的包
    public List<String> inPackages = new ArrayList<>();

    public String getPreName() {
        return preName;
    }

    public void setPreName(String preName) {
        this.preName = preName;
    }

    public String getNxtName() {
        return nxtName;
    }

    public void setNxtName(String nxtName) {
        this.nxtName = nxtName;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

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

    public List<String> getInPackages() {
        return inPackages;
    }

    public void setInPackages(List<String> inPackages) {
        this.inPackages = inPackages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
