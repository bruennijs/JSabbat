package sabbat.location.cep.unittests;

import com.google.common.collect.Lists;
import infrastructure.tracking.GeoPointBD;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sabbat.location.cep.flink.activity.ActivityPipeline;
import sabbat.location.cep.flink.activity.model.ActivityCoordinate;
import sabbat.location.cep.flink.activity.model.DeltaV;
import sabbat.location.cep.flink.activity.model.TimeSeriesCoordinate;
import sabbat.location.cep.test.flink.FlinkStreamingTestBase;
import sabbat.location.cep.test.flink.Sink2SubjectAdapter;
import test.matcher.LambdaMatcher;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by bruenni on 15.07.17.
 */
@RunWith(JUnit4.class)
public class DeltaVTests extends FlinkStreamingTestBase {
    @Test
    public void when_two_coordinates_expect_delta_calculated_to_output() throws Exception {

        Instant t1 = Instant.ofEpochSecond(0);
        Instant t2 = t1.plus(Duration.ofSeconds(10));

        GeoPointBD coordinate1 = GeoPointBD.parse("53.153121, 8.229314");
        GeoPointBD coordinate2 = GeoPointBD.parse("53.152561, 8.229743");

        String id = "p1";
        List<ActivityCoordinate> timeSeries = Arrays.asList(new ActivityCoordinate(id, new TimeSeriesCoordinate(t1, coordinate1)), new ActivityCoordinate(id, new TimeSeriesCoordinate(t2, coordinate2)));

        DataStreamSource<ActivityCoordinate> source = createLocalEnvironment().fromCollection(timeSeries);
        Sink2SubjectAdapter<Tuple2<ActivityCoordinate, DeltaV>> sink = new Sink2SubjectAdapter();

        DataStream<Tuple2<ActivityCoordinate, DeltaV>> distanceDeltaStream = new ActivityPipeline().createDistanceDeltaStream(source);

        distanceDeltaStream.addSink(sink);

        Iterable<Tuple2<ActivityCoordinate, DeltaV>> emitted = sink.getObservable().toBlocking().toIterable();

/*        Assert.assertThat(emitted, Matchers.containsInAnyOrder(LambdaMatcher.isMatching(new Predicate<Tuple2<ActivityCoordinate, DeltaV>>() {
        })));*/
    }

    @Test
    public void when_outlier_received_expect_not_used_for_distance() throws Exception {

    }
}
