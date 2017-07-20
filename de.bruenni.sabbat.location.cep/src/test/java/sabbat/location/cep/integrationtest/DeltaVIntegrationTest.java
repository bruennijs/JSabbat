package sabbat.location.cep.integrationtest;

import infrastructure.tracking.GeoPointBD;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DefaultTestContextBootstrapper;
import sabbat.location.cep.flink.activity.ActivityPipeline;
import sabbat.location.cep.flink.activity.model.ActivityCoordinate;
import sabbat.location.cep.flink.activity.model.ActivitySum;
import sabbat.location.cep.flink.activity.model.DeltaV;
import sabbat.location.cep.flink.activity.model.TimeSeriesCoordinate;
import sabbat.location.cep.test.flink.FlinkStreamingTestBase;
import sabbat.location.cep.test.flink.RxSink;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bruenni on 15.07.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "dev")
@BootstrapWith(DefaultTestContextBootstrapper.class)
@ContextConfiguration(classes = {FlinkIntegrationTestConfiguration.class})
public class DeltaVIntegrationTest extends FlinkStreamingTestBase {

    private static Logger logger = LoggerFactory.getLogger(DeltaVIntegrationTest.class);

/*    @Autowired
    @Qualifier("cassandraUserCoordinateRepository")
    public UserCoordinateRepository userCoordinateRepository;*/

    @Test
    public void when_two_coordinates_expect_delta_calculated_to_output() throws Exception {

        //ParameterTool parameterTool = ParameterTool.fromArgs(null);

        Configuration configuration = new Configuration();
        configuration.setLong("countWindowSize", 4);

        StreamExecutionEnvironment env = createLocalEnvironment();
        env.getConfig().setGlobalJobParameters(configuration);

        Instant t1 = Instant.now(Clock.systemUTC());
        Instant t2 = t1.plus(Duration.ofSeconds(10));
        Instant t3 = t2.plus(Duration.ofSeconds(10));
        Instant t4 = t3.plus(Duration.ofSeconds(10));

        GeoPointBD coordinate1 = GeoPointBD.parse("53.153121, 8.229314");
        GeoPointBD coordinate2 = GeoPointBD.parse("53.152561, 8.229743");
        GeoPointBD coordinate3 = GeoPointBD.parse("53.152561, 8.229989");
        GeoPointBD coordinate4 = GeoPointBD.parse("53.152561, 8.230089");

        String id = "p1";
        List<ActivityCoordinate> timeSeries = Arrays.asList(
                new ActivityCoordinate(id, new TimeSeriesCoordinate(t1, coordinate1)),
                new ActivityCoordinate(id, new TimeSeriesCoordinate(t2, coordinate2)),
                new ActivityCoordinate(id, new TimeSeriesCoordinate(t3, coordinate3)),
                new ActivityCoordinate(id, new TimeSeriesCoordinate(t4, coordinate4)));

        DataStreamSource<ActivityCoordinate> source = env.fromCollection(timeSeries);

        ActivityPipeline pipeline = new ActivityPipeline();

        // calculate keyed deltaV per coordinate
        DataStream<Tuple2<ActivityCoordinate, DeltaV>> distanceDeltaStream = pipeline.createDistanceDeltaStream(source);

        // calculate ActivitySum per all activity coordinates
        DataStream<ActivitySum> activitySumStream = pipeline.createActivitySum(distanceDeltaStream);

        distanceDeltaStream.addSink(createDeltavSl4jSink("deltavTest"));
        activitySumStream.addSink(createActivitySumSl4jSink("activitySumTest"));
        distanceDeltaStream.addSink(new RxSink<>());

        logger.debug("START execute");

        env.execute("DeltaVIntegrationTest");

        logger.debug("STOP execute");

        // ASSERTS
        //Iterable<Tuple2<ActivityCoordinate, DeltaV>> emitted = sink.getObservable(2).toBlocking().toIterable();

/*        Assert.assertThat(emitted, Matchers.contains(
                Arrays.asList(
                        LambdaMatcher.<Tuple2<ActivityCoordinate, DeltaV>>isMatching(t -> t.f1.getDuration().equals(Duration.ofSeconds(10)) ),
                        LambdaMatcher.<Tuple2<ActivityCoordinate, DeltaV>>isMatching(t -> t.f1.getDuration().equals(Duration.ofSeconds(20)) ))));*/
    }

    @Test
    public void when_outlier_received_expect_not_used_for_distance() throws Exception {

    }
}
