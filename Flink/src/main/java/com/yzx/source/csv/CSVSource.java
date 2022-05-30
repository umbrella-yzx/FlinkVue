package com.yzx.source.csv;

import com.yzx.source.config.CSVConfig;
import com.yzx.utils.ApplicationEnv;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.io.RowCsvInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.types.Row;

import java.util.ArrayList;
import java.util.Locale;


public class CSVSource{
    private CSVConfig csvConfig;
    private StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();

    public CSVSource(CSVConfig csvConfig) {
        this.csvConfig = csvConfig;
    }

    public DataStreamSource<Row> getDataSource(ArrayList<String> types){
        // 使用 RowCsvInputFormat 把每一行记录解析为一个 Row
        RowCsvInputFormat csvInput = new RowCsvInputFormat(
                new Path(csvConfig.getPath()),                                        // 文件路径
                getTypes(types),// 字段类型
                "\n",                                             // 行分隔符
                ",");                                            // 字段分隔符
        // 跳过第一行 表头
//        csvInput.setSkipFirstLineAsHeader(true);

        // 输入
        return env.readFile(csvInput, csvConfig.getPath());
    }

    private TypeInformation[] getTypes(ArrayList<String> types){
        TypeInformation[] typeInformation = new TypeInformation[types.size()];
        for(int i=0;i<types.size();++i){
            switch (types.get(i).toUpperCase(Locale.ROOT)){
                case "STRING":typeInformation[i] = Types.STRING;break;
                case "INT":typeInformation[i] = Types.INT;break;
                case "BYTE":typeInformation[i] = Types.BYTE;break;
                case "BOOLEAN":typeInformation[i] = Types.BOOLEAN;break;
                case "SHORT":typeInformation[i] = Types.SHORT;break;
                case "LONG":typeInformation[i] = Types.LONG;break;
                case "FLOAT":typeInformation[i] = Types.FLOAT;break;
                case "DOUBLE":typeInformation[i] = Types.DOUBLE;break;
                case "BIG_DEC":typeInformation[i] = Types.BIG_DEC;break;
                case "BIG_INT":typeInformation[i] = Types.BIG_INT;break;
                default:break;
            }
        }
        return typeInformation;
    }
}
