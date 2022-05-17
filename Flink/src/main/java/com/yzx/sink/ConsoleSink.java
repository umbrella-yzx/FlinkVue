package com.yzx.sink;

import com.yzx.utils.ApplicationEnv;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class ConsoleSink implements IFlinkSink {
    private StreamTableEnvironment tableEnv = ApplicationEnv.getTableEnvironment();

    @Override
    public void print(Table table) {
        tableEnv.toChangelogStream(table).print();
    }

    @Override
    public void print(TableResult table) {
        table.print();
    }
}
