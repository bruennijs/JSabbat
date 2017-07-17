package sabbat.location.cep.flink.activity.function;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import sabbat.location.cep.flink.activity.model.ActivityCoordinate;
import sabbat.location.cep.flink.activity.model.DeltaV;

import java.util.List;

/**
 * Created by bruenni on 17.07.17.
 */
public class DeltaVFlatMapFunction implements FlatMapFunction<List<Tuple2<ActivityCoordinate, DeltaV>>, Tuple2<ActivityCoordinate, DeltaV>> {
    @Override
    public void flatMap(List<Tuple2<ActivityCoordinate, DeltaV>> value, Collector<Tuple2<ActivityCoordinate, DeltaV>> out) throws Exception {
        value.stream().forEach(t -> out.collect(t));
    }
}
