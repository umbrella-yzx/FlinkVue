package com.yzx.process;

<#list filter.inPackages as package>
import ${package};
</#list>
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.types.Row;
import org.apache.flink.api.java.tuple.*;

public class ${filter.className} extends RichFilterFunction<${filter.outClass}> {
    @Override
    public boolean filter(${filter.outClass} value) throws Exception {
        return ${filter.condition};
    }
}