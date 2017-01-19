package sabbat.location.cep;

import infrastructure.util.IterableUtils;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.windowing.RichWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.api.windowing.windows.Window;
import sabbat.location.cep.flink.TimestampTuple2;

import java.util.stream.Collector;

/**
 * Created by bruenni on 19.01.17.
 */
public class DoubleAverageRichWindowFunction extends RichWindowFunction<TimestampTuple2<String, Double>, Double, String, TimeWindow> implements WindowFunction<TimestampTuple2<String, Double>, Double, String, TimeWindow> {

	private transient ValueState<Tuple2<Long, Double>> avgPerKey;

	@Override
	public void apply(String key, TimeWindow window, Iterable<TimestampTuple2<String, Double>> iterable, org.apache.flink.util.Collector<Double> collector) throws Exception {
		//// calculate max
		Tuple2<Long, Double> avgTuple = avgPerKey.value();

		org.apache.flink.api.java.tuple.Tuple2<Long, Double> windowAvg = IterableUtils.stream(iterable).reduce(new org.apache.flink.api.java.tuple.Tuple2<Long, Double>(),
			(acc, item) -> org.apache.flink.api.java.tuple.Tuple2.of(acc.f0 + 1, acc.f1 + item.getT2().doubleValue()),
			(acc1, acc2) -> org.apache.flink.api.java.tuple.Tuple2.of(acc1.f0 + acc2.f0, acc1.f1 + acc2.f1));

		avgTuple.f0 += windowAvg.f0;
		avgTuple.f1 += windowAvg.f1;

		avgPerKey.update(avgTuple);
	}

	@Override
	public void open(Configuration parameters) throws Exception {
		super.open(parameters);
		ValueStateDescriptor<Tuple2<Long, Double>> descriptor =
			new ValueStateDescriptor<>(
				"average", // the state name
				TypeInformation.of(new TypeHint<Tuple2<Long, Double>>() {}), // type information
				Tuple2.of(0L, 0d)); // default value of the state, if nothing was set
		avgPerKey = getRuntimeContext().getState(descriptor);
	}
}
