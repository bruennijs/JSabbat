package sabbat.location.cep;

import infrastructure.tracking.GeoPointBD;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.ApplicationContext;
import sabbat.location.cep.flink.activity.ActivityPipeline;
import sabbat.location.cep.flink.activity.model.ActivityCoordinate;
import sabbat.location.cep.flink.activity.model.DeltaV;
import sabbat.location.cep.flink.activity.model.TimeSeriesCoordinate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

/**
 * Created by bruenni on 03.07.16.
 */

//@SpringBootApplication
/*        (exclude =
        {
                org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration.class,
                org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.class
        })*/
public class Application {

    static Logger logger = org.slf4j.LoggerFactory.getLogger("console");

    public static void main(String[] args) throws IOException {

        Arrays.stream(args).forEach(arg -> logger.info("arg=" + arg));

        logger.info("Starting sabbat location Apache Flink CEP engine app");

        ApplicationContext applicationContext = SpringApplication.run(new Object[]
                {
                        CepAutoConfiguration.class,
                }, args);

        FlinkBootstrapper bootstrapper = new FlinkBootstrapper();

        ActivityPipeline activityPipeline = new ActivityPipeline();

        StreamExecutionEnvironment env = bootstrapper.createExecutionEnvironemnt();

        // load some sources
        DataStreamSource<ActivityCoordinate> source = env.fromCollection(Arrays.asList(new ActivityCoordinate("bla", new TimeSeriesCoordinate(Instant.now(), new GeoPointBD(BigDecimal.ONE, BigDecimal.TEN)))));

        DataStream<Tuple2<ActivityCoordinate, DeltaV>> stream = activityPipeline.createDistanceDeltaStream(source);

        stream.print();

    }
}
