package com.yzx.test;


import com.yzx.entity.MyScores;
import com.yzx.utils.ApplicationEnv;
import org.apache.flink.api.common.functions.MapFunction;

import org.apache.flink.api.java.tuple.Tuple5;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import org.apache.flink.streaming.api.functions.sink.filesystem.OutputFileConfig;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.core.fs.Path;
import org.apache.flink.api.common.serialization.Encoder;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class SinkCSVTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = ApplicationEnv.getEnvironment();

        DataStreamSource<MyScores> stream = env.fromElements(
                new MyScores(1, "Tom", 30, 50, 60),
                new MyScores(2, "Jack", 30, 50, 60),
                new MyScores(3, "Mary", 30, 50, 60),
                new MyScores(4, "Jean", 30, 50, 60),
                new MyScores(5, "Jim", 30, 50, 60));

        SingleOutputStreamOperator<Tuple5<Integer, String, Integer, Integer, Integer>> map = stream.map(new MapFunction<MyScores, Tuple5<Integer, String, Integer, Integer, Integer>>() {
            @Override
            public Tuple5<Integer, String, Integer, Integer, Integer> map(MyScores myScores) throws Exception {
                return new Tuple5<>(myScores.id,myScores.name,myScores.chinese,myScores.english,myScores.math);
            }
        });

        stream.addSink(StreamingFileSink.forRowFormat(new Path("F:input/myscores"),
                        new Encoder<MyScores>() {
                            @Override
                            public void encode(MyScores myScores, OutputStream outputStream) throws IOException {
                                Charset charset = Charset.forName("UTF-8");
                                outputStream.write(myScores.toCSVString().getBytes(charset));
                                outputStream.write(10);
                            }
                        })
                .withRollingPolicy(
                        DefaultRollingPolicy.builder()
                                //文件滚动间隔 每隔多久（指定）时间生成一个新文件
                                .withRolloverInterval(TimeUnit.SECONDS.toMillis(5))
                                //数据不活动时间 每隔多久（指定）未来活动数据，则将上一段时间（无数据时间段）也生成一个文件
                                .withInactivityInterval(TimeUnit.MINUTES.toMillis(5))
                                // 文件最大容量
                                .withMaxPartSize(1024 * 1024 * 1024)
                                .build())
                .withOutputFileConfig(OutputFileConfig
                        .builder()
                        .withPartSuffix(".csv")
                        .build())
                .build()).setParallelism(1);
        env.execute();
    }
}
