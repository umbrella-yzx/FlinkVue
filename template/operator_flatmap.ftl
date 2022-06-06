package ${flatmap.javaPackage};

<#list flatmap.inPackages as package>
    import ${package};
</#list>
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.api.java.tuple.*;

public class ${flatmap.className} extends RichFlatMapFunction<${flatmap.inClass}, ${flatmap.outClass}> {
    @Override
    public void flatMap(${flatmap.inClass} in, Collector<${flatmap.outClass}> out) throws Exception {
        ${flatmap.condition};
    }
}
