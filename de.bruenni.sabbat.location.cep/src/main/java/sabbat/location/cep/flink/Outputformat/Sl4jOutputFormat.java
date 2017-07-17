package sabbat.location.cep.flink.Outputformat;

import org.apache.flink.api.common.io.OutputFormat;
import org.apache.flink.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by bruenni on 17.07.17.
 */
public class Sl4jOutputFormat<T> implements OutputFormat<T> {

    private Logger Log = LoggerFactory.getLogger(Sl4jOutputFormat.class);

    private int taskNumber;
    private int numTasks;
    private String correlationId = "";

    public Sl4jOutputFormat(String correlationId) {

        this.correlationId = correlationId;
    }

    @Override
    public void configure(Configuration parameters) {

    }

    @Override
    public void open(int taskNumber, int numTasks) throws IOException {

        this.taskNumber = taskNumber;
        this.numTasks = numTasks;
    }

    @Override
    public void writeRecord(T record) throws IOException {
        Log.info("{} [cid={}, taskNo={}, numTasks={}]", record.toString(), this.correlationId, this.taskNumber, this.numTasks);
    }

    @Override
    public void close() throws IOException {
        this.taskNumber = 0;
        this.numTasks = 0;
    }
}
