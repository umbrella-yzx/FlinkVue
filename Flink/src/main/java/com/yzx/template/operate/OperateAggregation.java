package com.yzx.template.operate;

import com.yzx.test.Node;

/**
 * 滚动聚合节点
 */
public class OperateAggregation extends Node {
    //聚合的条件语句
    public String condition;

    public String outClass;

    /**
     * 0:sum():滚动聚合流过该算子的指定字段的和
     * 1:min():滚动计算流过该算子的指定字段的最小值
     * 2:max():滚动计算流过该算子的指定字段的最大值
     * 3:minBy():滚动计算当目前为止流过该算子的最小值，返回该值对应的事件
     * 4:maxBy():滚动计算当目前为止流过该算子的最大值，返回该值对应的事件
     */
    public int aggregationType;

    public OperateAggregation() {
        type = "Aggregation";
    }

    public String getOutClass() {
        return outClass;
    }

    public void setOutClass(String outClass) {
        this.outClass = outClass;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(int aggregationType) {
        this.aggregationType = aggregationType;
    }
}
