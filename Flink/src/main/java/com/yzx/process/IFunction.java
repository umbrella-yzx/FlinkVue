package com.yzx.process;

import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;

public interface IFunction {
    Table sqlQuery(String sql);
    TableResult executeSql(String sql);
}
