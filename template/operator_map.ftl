package ${map.javaPackage};

<#list map.inPackages as package>
import ${package};
</#list>
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.*;

public class ${map.className} extends RichMapFunction<${map.inClass}, ${map.outClass}> {
    @Override
    public ${map.outClass} map(${map.inClass} value) throws Exception {
        ${map.condition}
    }
}
