package sabbat.location.infrastructure.DtoToCommand;

import identity.UserRef;
import infrastructure.util.Tuple2;
import org.springframework.core.convert.converter.Converter;
import sabbat.location.api.dto.ActivityUpdateEventDto;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.application.service.command.TimeCoordinate;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 07.11.16.
 */
public class ActivityUpdateEventDtoConverter implements Converter<Tuple2<UserRef, ActivityUpdateEventDto>, ActivityUpdateCommand> {

    @Override
    public ActivityUpdateCommand convert(Tuple2<UserRef, ActivityUpdateEventDto> source) {
        return new ActivityUpdateCommand(source.getT1(),
            source.getT2().getId(),
            source.getT2().getTimeSeries().stream().map(ts ->
            {
                return new TimeCoordinate(ts.getLongitude(), ts.getLatitude(), ts.getTimestamp());
            }).collect(Collectors.toList()),
            Optional.empty(),
            Optional.empty());
    }
}
