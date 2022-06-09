package com.yzx.template.sink;

import com.yzx.source.config.CSVConfig;
import com.yzx.template.Node;

public class SinkCSV extends Node {
    public CSVConfig csvConfig;
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

    public Boolean getIsentity() {
        return isentity;
    }

    public void setIsentity(Boolean isentity) {
        this.isentity = isentity;
    }
}
