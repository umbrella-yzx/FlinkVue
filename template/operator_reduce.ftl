package ${reduce.javaPackage};

<#list reduce.inPackages as package>
    import ${package};
</#list>
import org.apache.flink.api.common.functions.RichReduceFunction;
import org.apache.flink.api.java.tuple.*;

public class ${reduce.className} extends RichReduceFunction<${reduce.outClass}> {
    @Override
    public ${reduce.outClass} reduce(${reduce.outClass} value1, ${reduce.outClass} value2) throws Exception {
        ${reduce.condition}
    }
}
