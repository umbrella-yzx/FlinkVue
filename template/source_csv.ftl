package ${csv.javaPackage};

<#list csv.inPackages as package>
import ${package};
</#list>
import com.yzx.source.config.CSVConfig;
import com.yzx.utils.ApplicationEnv;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class ${csv.className}<${csv.entity.className}> {
    private CSVConfig csvConfig;
    private StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();
    private StreamTableEnvironment tableEnv = ApplicationEnv.getTableEnvironment();

    public ${csv.className}(CSVConfig csvConfig) {
        this.csvConfig = csvConfig;
    }

    public DataStream<${csv.entity.className}> getCsvDataStream(){
        tableEnv.executeSql("DROP TABLE IF EXISTS `mytable`");
        tableEnv.executeSql("${csv.sql}");
        Table table = tableEnv.sqlQuery("select * from mytable");
        DataStream<${csv.entity.className}> sinkStream = tableEnv.toAppendStream(table, ${csv.entity.className}.class);
        return sinkStream;
    }
}