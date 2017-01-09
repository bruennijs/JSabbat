package sabbat.location.cep.unittests;

import com.google.common.collect.Lists;
import infrastructure.resources.Resources;
import lt.overdrive.trackparser.parsing.ParserException;
import lt.overdrive.trackparser.parsing.gpx.GpxParser;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sabbat.location.cep.test.flink.FlinkStreamingTestBase;
import sabbat.location.cep.test.flink.TimestampVector2;

import java.io.IOException;
import java.io.InputStream;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * Created by bruenni on 09.01.17.
 */
@RunWith(JUnit4.class)
public class GroupMemberEuclideanDistancePipelineUnitTest extends FlinkStreamingTestBase
{
    private StreamExecutionEnvironment localEnvironment;

    @Before
    public void before()
    {

        //Configuration conf = new Configuration();
        //conf.setFloat(ConfigConstants.DEFAULT_PARALLELISM, 0.5f);
        localEnvironment = createLocalEnvironment();
    }

    @Test
    public void when_run_simple_keyed_stream_expect_reduce_collects_results() throws Exception {
        Instant t1 = Instant.now(Clock.systemUTC());
        Instant t2 = t1.plus(Duration.ofSeconds(5));

        TimestampVector2<String, Double> v1 = new TimestampVector2<>("group1", 7.2d, t1);
        TimestampVector2<String, Double> v1 = new TimestampVector2<>("group1", 2.8d, t1);
        TimestampVector2<String, Double> v2 = new TimestampVector2<>("group2", 21.7d, t2);

        DataStreamSource<TimestampVector2<String, Double>> source = localEnvironment.fromCollection(Arrays.asList(v1, v2));

        source.keyBy(v -> v.getT1())
              .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
               .fold(Lists.newArrayList(), (collector, vcurrent) ->
                {
                    collector.add(vcurrent);
                    return collector;
                });

        localEnvironment.execute();
    }

    @Test
    public void when_two_coordinates_of_same_group_expect_group_distance_matrix_evented() throws ParserException, IOException {

        GpxParser gpxParser = new GpxParser();
        try (InputStream is = Resources.getResourceAsStream("gpx/file.gpx")) {
          gpxParser.parse(is);


        }
        finally {

        }
    }
}
