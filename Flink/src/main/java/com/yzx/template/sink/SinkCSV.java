package com.yzx.template.sink;

import com.yzx.source.config.CSVConfig;
import com.yzx.template.Node;
import com.yzx.template.entity.Entity;

public class SinkCSV extends Node {
    public CSVConfig csvConfig;
    //CSV对应POJO类
    public String outClass;
    //是否为entity
    public Boolean isentity;

    public SinkCSV() {
        type = "CSVSink";
        isentity = false;
    }

    public CSVConfig getCsvConfig() {
        return csvConfig;
    }

    public void setCsvConfig(CSVConfig csvConfig) {
        this.csvConfig = csvConfig;
    }

    public String getOutClass() {
        return outClass;
    }

    public void setOutClass(String outClass) {
        this.outClass = outClass;
    }

    public Boolean getIsentity() {
        return isentity;
    }

    public void setIsentity(Boolean isentity) {
        this.isentity = isentity;
    }
}
