package ${reduce.javaPackage};

<#list reduce.inPackages as package>
    import ${package};
</#list>
import org.apache.flink.api.common.functions.RichReduceFunction;
import org.apache.flink.api.java.tuple.*;

public class ${reduce.className} extends RichReduceFunction<${reduce.inClass}> {
    @Override
    public ${reduce.inClass} reduce(${reduce.inClass} value1, ${reduce.inClass} value2) throws Exception {
        ${reduce.condition};
    }
}
