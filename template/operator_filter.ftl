package com.yzx.process;

<#list filter.inPackages as package>
import ${package};
</#list>
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.types.Row;

public class ${filter.className} extends RichFilterFunction<${filter.inClass}> {
    @Override
    public boolean filter(${filter.inClass} value) throws Exception {
        return ${filter.condition};
    }
}