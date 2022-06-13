package com.yzx.template.entity;

public class Property {
    // 属性数据类型
    private String javaType;
    // 属性名称
    private String propertyName;
    //后缀，生成代码用
    private String suffix;

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    private PropertyType propertyType;

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    @Override
    public String toString() {
        return "Property{" +
                "javaType='" + javaType + '\'' +
                ", propertyName='" + propertyName + '\'' +
                ", propertyType=" + propertyType +
                '}';
    }
}
