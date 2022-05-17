package com.yzx.sink;

import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;

public interface IFlinkSink {
    void print(Table table);
    void print(TableResult table);
}
