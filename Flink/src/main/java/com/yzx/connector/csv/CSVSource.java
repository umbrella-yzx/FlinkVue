package com.yzx.connector.csv;

import com.yzx.connector.config.CSVConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.core.fs.Path;
import org.apache.flink.formats.csv.CsvReaderFormat;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

public class CSVSource extends RichSourceFunction<Object> {
    private volatile boolean isRunning = true;
    private CSVConfig csvConfig;

    public CSVSource(CSVConfig csvConfig) {
        this.csvConfig = csvConfig;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);

    }

    @Override
    public void run(SourceContext<Object> sourceContext) throws Exception {
        CsvReaderFormat<Object> csvFormat = CsvReaderFormat.forPojo(Object.class);
    }

    @Override
    public void cancel() {

    }
}
