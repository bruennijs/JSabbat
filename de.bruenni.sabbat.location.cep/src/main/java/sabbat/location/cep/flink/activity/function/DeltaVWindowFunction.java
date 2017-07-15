package sabbat.location.cep.flink.activity.function;

import infrastructure.util.IterableUtils;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.shaded.com.google.common.collect.Lists;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.Window;
import org.apache.flink.util.Collector;
import org.springframework.util.StreamUtils;
import sabbat.location.cep.flink.activity.model.ActivityCoordinate;
import sabbat.location.cep.flink.activity.model.DeltaV;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.BiFunction;

/**
 * Created by bruenni on 15.07.17.
 * Can calculate the euclidena distance between the coordinates of this window.
 * Example: x input coordinates -> emits latest x-1 Tuple with DeltaV to out collector.
 */
public class DeltaVWindowFunction implements WindowFunction<ActivityCoordinate, Tuple2<ActivityCoordinate, DeltaV>, String, Window> {

    @Override
    public void apply(String s, Window window, Iterable<ActivityCoordinate> iterable, Collector<Tuple2<ActivityCoordinate, DeltaV>> collector) throws Exception {
/*        IterableUtils.stream(iterable)
                .sorted(ActivityCoordinate.comparatorByTimeStamp())
                .reduce(Lists.<Tuple2<ActivityCoordinate, DeltaV>>newArrayListWithCapacity(3), new BiFunction<ArrayList<Tuple2<ActivityCoordinate, DeltaV>>, ActivityCoordinate, ArrayList<Tuple2<ActivityCoordinate, DeltaV>>>() {
                    @Override
                    public ArrayList<Tuple2<ActivityCoordinate, DeltaV>> apply(ArrayList<Tuple2<ActivityCoordinate, DeltaV>> tuple2s, ActivityCoordinate activityCoordinate) {
                        return null;
                    }
                });*/

    }
}
