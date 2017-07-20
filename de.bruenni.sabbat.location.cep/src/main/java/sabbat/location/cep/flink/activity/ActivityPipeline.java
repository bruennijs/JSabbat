package sabbat.location.cep.flink.activity;

import com.sun.javafx.scene.control.skin.SliderSkin;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;
import org.apache.flink.streaming.api.windowing.assigners.SlidingAlignedProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.evictors.CountEvictor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.apache.flink.streaming.api.windowing.windows.Window;
import org.apache.flink.util.Collector;
import sabbat.location.cep.flink.activity.function.*;
import sabbat.location.cep.flink.activity.model.ActivityCoordinate;
import sabbat.location.cep.flink.activity.model.ActivityMetric;
import sabbat.location.cep.flink.activity.model.ActivitySum;
import sabbat.location.cep.flink.activity.model.DeltaV;
import sabbat.location.cep.flink.evictor.LambdaEvictor;

import java.util.List;

/**
 * Created by bruenni on 15.07.17.
 */
public class ActivityPipeline {

    /**
     * Calculates delta of distance and duration between current coordinate and the former in point in time
     * @param input
     * @return
     */
    public DataStream<Tuple2<ActivityCoordinate, DeltaV>> createDistanceDeltaStream(DataStream<ActivityCoordinate> input)
    {
        // decouple already emitted deltas and not in-order received coordinates and remove them from stream
        return input
                .assignTimestampsAndWatermarks(new ActivityCoordinateWatermarkAssigner(Time.seconds(60)))
                .setParallelism(1)
                .keyBy("activityId")
                .window(GlobalWindows.create())
                .evictor(CountEvictor.of(1, true))  // remove all except the latest item in the window so that aggregate has one item to calculate delta to
                .trigger(CountTrigger.of(4))    // the smallest size to calculate delta
                .aggregate(new DeltaVAggregateFunction())
                .flatMap(new DeltaVFlatMapFunction());
    }

    public DataStream<ActivitySum> createActivitySum(DataStream<Tuple2<ActivityCoordinate, DeltaV>> distanceDeltaStream)
    {
        return distanceDeltaStream
                .keyBy(new KeySelector<Tuple2<ActivityCoordinate,DeltaV>, String>() {
                    @Override
                    public String getKey(Tuple2<ActivityCoordinate, DeltaV> value) throws Exception {
                        return value.f0.getActivityId();
                    }
                })
                .map(new ActivitySumRichMapFunction());
    }

    /**
     * Calculates delta of distance and duration between current coordinate and the former in point in time
     * @param input
     * @return
     */
    public DataStream<ActivityCoordinate> createDistanceDeltaStreamFromMultiple(DataStream<List<ActivityCoordinate>> input)
    {
        // decouple already emitted deltas and not in-order received coordinates and remove them from stream
        return input.flatMap(new FlatMapFunction<List<ActivityCoordinate>, ActivityCoordinate>() {
            @Override
            public void flatMap(List<ActivityCoordinate> value, Collector<ActivityCoordinate> out) throws Exception {
                value.stream().forEach(ac -> out.collect(ac));
            }
        });
    }
}
