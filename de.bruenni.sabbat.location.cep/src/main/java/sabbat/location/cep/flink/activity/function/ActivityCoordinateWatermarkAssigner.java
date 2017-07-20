package sabbat.location.cep.flink.activity.function;

import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import sabbat.location.cep.flink.activity.model.ActivityCoordinate;

/**
 * Created by bruenni on 18.07.17.
 */
public class ActivityCoordinateWatermarkAssigner extends BoundedOutOfOrdernessTimestampExtractor<ActivityCoordinate> {

    public ActivityCoordinateWatermarkAssigner(Time maxOutOfOrderness) {
        super(maxOutOfOrderness);
    }

    @Override
    public long extractTimestamp(ActivityCoordinate element) {
        return element.getCoordinate().getTimestamp().toEpochMilli();
    }
}
