package sabbat.location.cep.flink.activity.function;

import com.google.common.collect.Lists;
import infrastructure.tracking.Metrics;
import infrastructure.tracking.distance.Distance;
import infrastructure.tracking.distance.EuclideanDistance2;
import infrastructure.tracking.distance.GeoDistance;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import sabbat.location.cep.flink.activity.model.ActivityCoordinate;
import sabbat.location.cep.flink.activity.model.DeltaV;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 15.07.17.
 * Can calculate the euclidena distance between the coordinates of this window.
 * Example: x input coordinates -> emits latest x-1 Tuple with DeltaV to out collector.
 */
public class DeltaVAggregateFunction implements AggregateFunction<ActivityCoordinate, ArrayList<Tuple2<ActivityCoordinate, DeltaV>>, List<Tuple2<ActivityCoordinate, DeltaV>>> {

    private static GeoDistance distanceStrategy = new EuclideanDistance2();

    @Override
    public ArrayList<Tuple2<ActivityCoordinate, DeltaV>> createAccumulator() {
        return Lists.newArrayList();
    }

    /**
     * Calculates deltaS and deltaT to latest coordinate in accumulator.
     * @param value
     * @param accumulator
     */
    @Override
    public void add(ActivityCoordinate value, ArrayList<Tuple2<ActivityCoordinate, DeltaV>> accumulator) {
        if (accumulator.size() == 0)
        {
            // if accumulator is empty it just adds it to the accumulator. But must be removed for the result.
            accumulator.add(new Tuple2<>(value, null));
        }
        else {
            // else accumulator contains first received coordinate already
            // -> take last item in list and calculate distance delta
            Tuple2<ActivityCoordinate, DeltaV> latestAccTuple = accumulator.get(accumulator.size() - 1);
            Distance<Double> distance = distanceStrategy.distance(
                    value.getCoordinate().getCoordinate().toDouble(),
                    latestAccTuple.f0.getCoordinate().getCoordinate().toDouble(),
                    Metrics.METER);

            // 2. calculate duration in SEC
            Duration duration = Duration.between(value.getCoordinate().getTimestamp(), latestAccTuple.f0.getCoordinate().getTimestamp());

            accumulator.add(new Tuple2<>(value, new DeltaV(Distance.from(BigDecimal.valueOf(distance.get()), distance.getMetric()), duration)));
        }
    }

    /**
     * Just take the values with DeltaV. The coordinates withoout DeltaV should be calculated by overlapping windows.
     * @param accumulator
     * @return
     */
    @Override
    public List<Tuple2<ActivityCoordinate, DeltaV>> getResult(ArrayList<Tuple2<ActivityCoordinate, DeltaV>> accumulator) {
        return accumulator.stream().filter(t -> t.f1 != null).collect(Collectors.toList());
    }

    @Override
    public ArrayList<Tuple2<ActivityCoordinate, DeltaV>> merge(ArrayList<Tuple2<ActivityCoordinate, DeltaV>> a, ArrayList<Tuple2<ActivityCoordinate, DeltaV>> b) {
        a.addAll(b);
        return a;
    }
}
