package com.yzx.template.operate;

import com.yzx.test.Node;

import java.util.List;

/**
 * 数据量合并操作节点
 * DataStream* → DataStream
 */
public class OperateUnion extends Node {
    //聚合的数据流名称
    public String condition;

    public OperateUnion() {
        type = "Union";
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    //临时的前置变量名集合，用于生成preName和condition
    public void setConditionAndPreName(List<String> list){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if(i==0){
                preName = list.get(i);
            }else{
                sb.append(list.get(i));
                if(i!= list.size()-1)sb.append(',');
            }
        }
    }
}
