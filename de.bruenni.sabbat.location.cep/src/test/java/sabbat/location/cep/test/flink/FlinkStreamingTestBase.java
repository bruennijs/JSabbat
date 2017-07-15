package sabbat.location.cep.test.flink;

import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.IOException;

/**
 * Created by bruenni on 09.01.17.
 */
public abstract class FlinkStreamingTestBase {
  protected StreamExecutionEnvironment createLocalEnvironment() throws IOException {
      StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
      env.setParallelism(2);
      env.setStateBackend(new FsStateBackend("file:////tmp/flink"));
      //env.enableCheckpointing(1000);
      env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
      return env;
  }
}
