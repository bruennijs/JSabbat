package sabbat.location.cep.unittests;

import com.google.common.collect.Lists;
import com.hazelcast.logging.ILogger;
import de.bruenni.infrastructure.tracking.file.LtOverdriveGpxTrackFileParser;
import infrastructure.resources.Resources;
import infrastructure.time.BucketAdjuster;
import infrastructure.tracking.Track;
import infrastructure.tracking.TrackPoint;
import infrastructure.tracking.file.ITrackFileParser;
import infrastructure.tracking.file.TrackFileParserException;
import infrastructure.util.IterableUtils;
import lt.overdrive.trackparser.domain.Trail;
import lt.overdrive.trackparser.parsing.ParserException;
import lt.overdrive.trackparser.parsing.gpx.GpxParser;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.LoggerFactory;
import sabbat.location.cep.test.flink.FlinkStreamingTestBase;
import sabbat.location.cep.test.flink.Sink2SubjectAdapter;
import sabbat.location.cep.test.flink.TimestampVector2;

import java.io.IOException;
import java.io.InputStream;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by bruenni on 09.01.17.
 */
@RunWith(JUnit4.class)
public class GroupMemberEuclideanDistancePipelineUnitTest extends FlinkStreamingTestBase
{
		private static org.slf4j.Logger log = LoggerFactory.getLogger(GroupMemberEuclideanDistancePipelineUnitTest.class);

    private StreamExecutionEnvironment localEnvironment;

    private ITrackFileParser gpxParser = new LtOverdriveGpxTrackFileParser();

    @Before
    public void before()
    {

        //Configuration conf = new Configuration();
        //conf.setFloat(ConfigConstants.DEFAULT_PARALLELISM, 0.5f);
        localEnvironment = createLocalEnvironment();
    }

    @Test
    public void when_run_simple_keyed_stream_expect_reduce_collects_results() throws Exception {

      Sink2SubjectAdapter<Double> adapter = new Sink2SubjectAdapter<>();

      Instant t1 = Instant.now(Clock.systemUTC());
        Instant t2 = t1.plus(Duration.ofSeconds(5));

        TimestampVector2<String, Double> v1 = new TimestampVector2<>("group1", 7.2d, t1);
        TimestampVector2<String, Double> v2 = new TimestampVector2<>("group1", 2.8d, t1);
        TimestampVector2<String, Double> v3 = new TimestampVector2<>("group2", 9.5d, t2);
        TimestampVector2<String, Double> v4 = new TimestampVector2<>("group2", 12.5d, t2);

        DataStreamSource<TimestampVector2<String, Double>> source = localEnvironment.fromCollection(Arrays.asList(v1, v2, v3));
        source.keyBy(v -> v.getT1())
            .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
            .apply(new WindowFunction<TimestampVector2<String,Double>, Double, String, TimeWindow>() {

                public ValueState<Tuple2<Long, Double>> avgData;

                @Override
                public void apply(String key, TimeWindow timeWindow, Iterable<TimestampVector2<String, Double>> iterable, Collector<Double> collector) throws Exception {
                    //// calculate max
                    Tuple2<Long, Double> avgTuple = avgData.value();

                    Tuple2<Long, Double> windowAvg = IterableUtils.stream(iterable).reduce(new Tuple2<Long, Double>(),
                      (acc, item) -> Tuple2.of(acc.f0 + 1, acc.f1 + item.getT2().doubleValue()),
                      (acc1, acc2) -> Tuple2.of(acc1.f0 + acc2.f0, acc1.f1 + acc2.f1));

                    avgTuple.f0 += windowAvg.f0;
                    avgTuple.f1 += windowAvg.f1;

                    avgData.update(avgTuple);
                }
            })
            .filter(avg -> avg > 10.0 )
            .addSink(adapter);

      localEnvironment.execute();

      List<Double> valueList = IterableUtils.toList(adapter.getObservable()
          .timeout(5, TimeUnit.SECONDS)
          .toBlocking()
          .toIterable());

			Assert.assertThat(valueList, org.hamcrest.core.IsCollectionContaining.hasItems(Double.valueOf(11.0)));
    }

    @Test
    public void when_two_coordinates_of_same_group_expect_group_distance_matrix_evented() throws ParserException, IOException, TrackFileParserException {

        try (InputStream is = Resources.getResourceAsStream("gpx/ga1.gpx")) {
					Iterable<Track> tracks = gpxParser.parse(is);

					Stream<TrackPoint> trackPointStream = IterableUtils.stream(IterableUtils.stream(tracks).findFirst().get().getPoints());

					Map<Instant, List<infrastructure.util.Tuple2>> trackPointsGroup = trackPointStream
							.filter(tp -> tp.getTime().isPresent())
							.map(tp -> new infrastructure.util.Tuple2<Instant, TrackPoint>(getBucket(tp), tp))
							.collect(Collectors.groupingBy(tuple -> (Instant)tuple.getT1()));


					//trackPointsGroup.putAll();values().stream().collect(Collectors.)
					trackPointsGroup.forEach((k, v) -> log.info(String.format("%1s->%2d", k.toString(), v.size())));
				}
        finally {
        }
    }

	private Instant getBucket(TrackPoint tp) {
		return tp.getTime().get().with(new BucketAdjuster(Duration.ofMinutes(5)));
	}
}
