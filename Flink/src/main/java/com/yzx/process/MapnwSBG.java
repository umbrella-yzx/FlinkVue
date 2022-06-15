package com.yzx.process;

import com.yzx.entity.User;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.*;

public class MapnwSBG extends RichMapFunction<User, Tuple2<Integer,Integer>> {
    @Override
    public Tuple2<Integer,Integer> map(User value) throws Exception {
        return Tuple2.of(value.age, 1);
    }
}
