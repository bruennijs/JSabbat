package sabbat.location.infrastructure.DtoToCommand;

import infrastructure.identity.Token;
import jdk.nashorn.internal.parser.DateParser;
import org.springframework.core.convert.converter.Converter;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.application.service.command.TimeCoordinate;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityUpdateEventDto;

import java.util.stream.Collectors;

/**
 * Created by bruenni on 07.11.16.
 */
public class ActivityUpdateEventDtoConverter implements Converter<ActivityUpdateEventDto, ActivityUpdateCommand> {
    @Override
    public ActivityUpdateCommand convert(ActivityUpdateEventDto source) {
        return new ActivityUpdateCommand(Token.valueOf(source.getIdentityToken()),
                source.getId(),
                source.getTimeSeries().stream().map(ts ->
                {
                    return new TimeCoordinate(ts.getLongitude(), ts.getLatitude(), ts.getTimestamp());
                }).collect(Collectors.toList()),
                null,
                null);
    }
}
