package ${keyby.javaPackage};

<#list keyby.inPackages as package>
    import ${package};
</#list>
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.types.Row;
import org.apache.flink.api.java.tuple.*;

public class ${keyby.className} implements KeySelector<${keyby.inClass},${keyby.outClass}> {
    @Override
    public ${keyby.outClass} getKey(${keyby.inClass} value) throws Exception {
        return ${keyby.condition};
    }
}
