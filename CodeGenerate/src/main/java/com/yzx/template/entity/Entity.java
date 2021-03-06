package com.yzx.template.entity;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    // 实体所在的包名
    private String javaPackage;
    // 实体类名
    private String className;
    // 父类名
    private String superclass;
    // 属性集合
    List<Property> properties;
    // 是否有构造函数
    private boolean constructors;
    //需要导入的包
    private List<String> inPackages = new ArrayList<>();

    public List<String> getInPackages() {
        return inPackages;
    }

    public void setInPackages(List<String> inPackages) {
        this.inPackages = inPackages;
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

    public String getSuperclass() {
        return superclass;
    }

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public boolean isConstructors() {
        return constructors;
    }

    public void setConstructors(boolean constructors) {
        this.constructors = constructors;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "javaPackage='" + javaPackage + '\'' +
                ", className='" + className + '\'' +
                ", superclass='" + superclass + '\'' +
                ", properties=" + properties.toString() +
                ", constructors=" + constructors +
                '}';
    }
}
