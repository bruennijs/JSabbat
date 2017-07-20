package sabbat.location.cep.test.flink;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.OutputFormatSinkFunction;
import sabbat.location.cep.flink.Outputformat.Sl4jOutputFormat;
import sabbat.location.cep.flink.activity.model.ActivityCoordinate;
import sabbat.location.cep.flink.activity.model.ActivitySum;
import sabbat.location.cep.flink.activity.model.DeltaV;

import java.io.IOException;

/**
 * Created by bruenni on 09.01.17.
 */
public abstract class FlinkStreamingTestBase {
  protected StreamExecutionEnvironment createLocalEnvironment() throws IOException {
      StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
      env.setParallelism(1);
      env.setStateBackend(new FsStateBackend("file:////tmp/flink"));
      //env.enableCheckpointing(1000);
      env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
      return env;
  }

    protected OutputFormatSinkFunction<Tuple2<ActivityCoordinate, DeltaV>> createDeltavSl4jSink(String correlationId) {
        return new OutputFormatSinkFunction<>(new Sl4jOutputFormat<>(correlationId));
    }

    protected OutputFormatSinkFunction<ActivitySum> createActivitySumSl4jSink(String correlationId) {
        return new OutputFormatSinkFunction<>(new Sl4jOutputFormat<>(correlationId));
    }
}
