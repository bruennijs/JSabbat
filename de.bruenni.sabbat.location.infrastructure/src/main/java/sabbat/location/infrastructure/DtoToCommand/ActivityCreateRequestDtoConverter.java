package sabbat.location.infrastructure.DtoToCommand;

import infrastructure.identity.Token;
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
public class ActivityCreateRequestDtoConverter implements Converter<ActivityCreateRequestDto, ActivityCreateCommand> {
    @Override
    public ActivityCreateCommand convert(ActivityCreateRequestDto source) {
        return new ActivityCreateCommand(Token.valueOf(source.getIdentityToken()),
                source.getId(),
                source.getTitle());
    }
}
