package sabbat.location.cep.flink.activity.function;

import infrastructure.tracking.GeoPoint;
import infrastructure.tracking.Metric;
import infrastructure.tracking.Metrics;
import infrastructure.tracking.distance.Distance;
import infrastructure.tracking.distance.EuclideanDistance2;
import infrastructure.tracking.distance.GeoDistance;
import infrastructure.util.IterableUtils;
import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.shaded.com.google.common.collect.Lists;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.Window;
import org.apache.flink.util.Collector;
import sabbat.location.cep.flink.activity.model.ActivityCoordinate;
import sabbat.location.cep.flink.activity.model.DeltaV;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.function.BiFunction;

/**
 * Created by bruenni on 15.07.17.
 * Can calculate the euclidena distance between the coordinates of this window.
 * Example: x input coordinates -> emits latest x-1 Tuple with DeltaV to out collector.
 */
public class DeltaVFoldFunction implements FoldFunction<ActivityCoordinate, Tuple2<ActivityCoordinate, DeltaV>> {

    private static GeoDistance distanceStrategy = new EuclideanDistance2();

    @Override
    public Tuple2<ActivityCoordinate, DeltaV> fold(Tuple2<ActivityCoordinate, DeltaV> accumulator, ActivityCoordinate value) throws Exception {
        if (accumulator == null)
        {
            return new Tuple2<>(value, DeltaV.empty());
        }

        // else accumulator contains first received coordinate
        // 1. calculate distance delta
        Distance<Double> distance = distanceStrategy.distance(
                value.getCoordinate().getCoordinate(),
                accumulator.f0.getCoordinate().getCoordinate(),
                Metrics.METER);

        // 2. calculate duration in SEC
        Duration duration = Duration.between(value.getCoordinate().getTimestamp(), accumulator.f0.getCoordinate().getTimestamp());

        accumulator.f1 = new DeltaV(Distance.from(BigDecimal.valueOf(distance.get()), distance.getMetric()), duration);
        accumulator.f0 = value;

        return accumulator;
    }
}
