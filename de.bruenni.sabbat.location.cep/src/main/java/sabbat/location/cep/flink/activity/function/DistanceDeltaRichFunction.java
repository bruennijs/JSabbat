package sabbat.location.cep.flink.activity.function;

import org.apache.flink.api.common.functions.RichFlatJoinFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import sabbat.location.api.dto.ActivityUpdateEventDto;

import java.math.BigDecimal;

/**
 * Created by bruenni on 15.07.17.
 */
public class DistanceDeltaRichFunction extends RichMapFunction<ActivityUpdateEventDto, Tuple2<ActivityUpdateEventDto, BigDecimal>> {
    @Override
    public Tuple2<ActivityUpdateEventDto, BigDecimal> map(ActivityUpdateEventDto value) throws Exception {
        return null;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        //getRuntimeContext().
    }
}
