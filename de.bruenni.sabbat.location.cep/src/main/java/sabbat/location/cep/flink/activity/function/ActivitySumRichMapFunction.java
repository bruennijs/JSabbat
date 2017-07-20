package sabbat.location.cep.flink.activity.function;

import infrastructure.tracking.Metrics;
import infrastructure.tracking.distance.Distance;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import sabbat.location.cep.flink.activity.model.ActivityCoordinate;
import sabbat.location.cep.flink.activity.model.ActivitySum;
import sabbat.location.cep.flink.activity.model.DeltaV;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

/**
 * Created by bruenni on 18.07.17.
 */
public class ActivitySumRichMapFunction extends RichMapFunction<Tuple2<ActivityCoordinate, DeltaV>, ActivitySum> {

    private transient ValueState<Tuple3<Instant, Distance<BigDecimal>, Duration>> activityDistanceAndDuration;

    @Override
    public ActivitySum map(Tuple2<ActivityCoordinate, DeltaV> value) throws Exception {
        Tuple3<Instant, Distance<BigDecimal>, Duration> state = getState();

        if (value.f0.getCoordinate().getTimestamp().isAfter(state.f0))  //this should be true all the time cause window and count window should fire in order , also with outliers or delete them when too late
        {
            state.f0 = value.f0.getCoordinate().getTimestamp();
            state.f1 = state.f1.plus(value.f1.getDistance());
            state.f2 = state.f2.plus(value.f1.getDuration());

            // update state
            activityDistanceAndDuration.update(state);
        }

        return new ActivitySum(state.f1, state.f2);
    }

    private Tuple3<Instant, Distance<BigDecimal>, Duration> getState() throws IOException {
        Tuple3<Instant, Distance<BigDecimal>, Duration> state = activityDistanceAndDuration.value();
        if (state == null)
            return new Tuple3<>(Instant.MIN, Distance.from(BigDecimal.ZERO, Metrics.METER), Duration.ofSeconds(0));

        return state;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ValueStateDescriptor descriptor = new ValueStateDescriptor("activityDistanceAndDuration", TypeInformation.of(new TypeHint<Tuple3<Instant, Distance<BigDecimal>, Duration>>(){}));
        this.activityDistanceAndDuration = this.getRuntimeContext().getState(descriptor);
    }
}
