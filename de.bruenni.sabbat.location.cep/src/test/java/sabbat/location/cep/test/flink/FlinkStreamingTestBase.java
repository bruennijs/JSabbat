package sabbat.location.cep.test.flink;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by bruenni on 09.01.17.
 */
public abstract class FlinkStreamingTestBase {
  protected StreamExecutionEnvironment createLocalEnvironment() {
      StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
      env.setParallelism(1);
      env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
      return env;
  }
}
