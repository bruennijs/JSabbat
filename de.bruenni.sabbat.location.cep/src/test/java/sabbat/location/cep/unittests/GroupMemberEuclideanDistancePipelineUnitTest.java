package sabbat.location.cep.unittests;

import de.bruenni.infrastructure.tracking.file.LtOverdriveGpxTrackFileParser;
import infrastructure.resources.Resources;
import infrastructure.time.BucketAdjuster;
import infrastructure.tracking.Track;
import infrastructure.tracking.TrackPoint;
import infrastructure.tracking.file.ITrackFileParser;
import infrastructure.tracking.file.TrackFileParserException;
import infrastructure.util.IterableUtils;
import lt.overdrive.trackparser.parsing.ParserException;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.RichWindowFunction;
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
import sabbat.location.cep.DoubleAverageRichWindowFunction;
import sabbat.location.cep.test.flink.FlinkStreamingTestBase;
import sabbat.location.cep.test.flink.Sink2SubjectAdapter;
import sabbat.location.cep.flink.TimestampTuple2;

import java.io.IOException;
import java.io.InputStream;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
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
		Instant t3 = t1.plus(Duration.ofSeconds(35));

      TimestampTuple2<String, Double> v1 = new TimestampTuple2<>("group1", 7.2d, t1);
      TimestampTuple2<String, Double> v2 = new TimestampTuple2<>("group1", 2.8d, t2);
      TimestampTuple2<String, Double> v3 = new TimestampTuple2<>("group2", 9.5d, t1);
      TimestampTuple2<String, Double> v4 = new TimestampTuple2<>("group2", 12.5d, t2);
      TimestampTuple2<String, Double> v5 = new TimestampTuple2<>("group2", 12.5d, t2);

      DataStreamSource<TimestampTuple2<String, Double>> source = localEnvironment.fromCollection(Arrays.asList(v1, v2, v3, v4, v5));
      source.keyBy(v -> v.getT1())
            .window(TumblingProcessingTimeWindows.of(Time.seconds(30)))
            .apply(new DoubleAverageRichWindowFunction())
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

			Iterable<Track> tracks1 = loadTracks("gpx/ga1.gpx");
			Track track1 = IterableUtils.stream(tracks1).findFirst().get();
			Map<Instant, Set<infrastructure.util.Tuple2<Track, TrackPoint>>> trackPointsGroup1 = loadTrackPointMap(track1);

			Iterable<Track> tracks2 = loadTracks("gpx/112km.gpx");
			Track track2 = IterableUtils.stream(tracks2).findFirst().get();
			Map<Instant, Set<infrastructure.util.Tuple2<Track, TrackPoint>>> trackPointsGroup2 = loadTrackPointMap(track2);

					//trackPointsGroup.putAll();values().stream().collect(Collectors.)
			//trackPointsGroup1.forEach((k, v) -> log.info(String.format("%3s: %1s->%2s", k.toString(), v.size(), track1.getName())));
			//trackPointsGroup2.forEach((k, v) -> log.info(String.format("%3s: %1s->%2s", k.toString(), v.size(), track2.getName())));

			trackPointsGroup1.forEach((k, v) -> trackPointsGroup2.merge(k,
			v,
			(v1, v2) ->
			{
				v1.addAll(v2);
				return v1;
			} ));


		//trackPointsGroup2.forEach((k, set) -> log.info(String.format("%3s: %1s->%2s", k.toString(), set.size(), set.stream().reduce(new StringBuilder(), (builder, tuple) -> builder.append("[" + tuple.getT1().getName().substring(0, 3) + ":" + tuple.getT2().getTime() + "]"), (sb1, sb2) -> sb1.append(sb2)))));
    }

	private Iterable<Track> loadTracks(String resourceName) throws TrackFileParserException, IOException {
		try (InputStream is = Resources.getResourceAsStream(resourceName)) {
			return  gpxParser.parse(is);
		}
		finally{}
	}

	private Map<Instant, Set<infrastructure.util.Tuple2<Track, TrackPoint>>> loadTrackPointMap(Track track) {
		Stream<TrackPoint> trackPointStream = IterableUtils.stream(track.getPoints());

		return trackPointStream
				.filter(tp -> tp.getTime().isPresent())
				.collect(Collectors.groupingBy(tp -> (Instant)getBucket(tp),
						                           Collectors.mapping(
						                           			trpo -> new infrastructure.util.Tuple2<Track, TrackPoint>(track, trpo),
																					 	Collectors.toSet())));
	}

	private Instant getBucket(TrackPoint tp) {
		return tp.getTime().get().with(new BucketAdjuster(Duration.ofMinutes(5)));
	}
}
