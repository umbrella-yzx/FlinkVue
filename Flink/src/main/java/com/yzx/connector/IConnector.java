package com.yzx.connector;

import org.apache.flink.table.api.TableResult;

public interface IConnector {
    public TableResult connect(String sql);
}
