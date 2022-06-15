package com.yzx.process;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.types.Row;
import org.apache.flink.api.java.tuple.*;

public class KeySelectetEEq implements KeySelector<Tuple2<Integer,Integer>,Integer> {
    @Override
    public Integer getKey(Tuple2<Integer,Integer> value) throws Exception {
        return value.f1;
    }
}
