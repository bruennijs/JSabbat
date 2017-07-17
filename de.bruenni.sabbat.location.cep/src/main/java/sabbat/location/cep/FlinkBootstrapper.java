package sabbat.location.cep;

import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamContextEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.IOException;

/**
 * Created by bruenni on 16.07.17.
 */
public class FlinkBootstrapper {
    public StreamExecutionEnvironment createExecutionEnvironemnt() throws IOException {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.setParallelism(2);    // done somewhere else by cli?
        env.setStateBackend(new FsStateBackend("file:////tmp/flink"));
        //env.enableCheckpointing(1000);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        return env;
    }
}
