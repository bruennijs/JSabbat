package sabbat.location.infrastructure.DtoToCommand;

import identity.UserRef;
import infrastructure.util.Tuple2;
import org.springframework.core.convert.converter.Converter;
import sabbat.location.api.dto.ActivityCreateRequestDto;
import sabbat.location.core.application.service.command.ActivityCreateCommand;

import java.util.stream.Collectors;

/**
 * Created by bruenni on 07.11.16.
 */
public class ActivityCreateRequestDtoConverter implements Converter<Tuple2<UserRef, ActivityCreateRequestDto>, ActivityCreateCommand> {

    @Override
    public ActivityCreateCommand convert(Tuple2<UserRef, ActivityCreateRequestDto> source) {
        return new ActivityCreateCommand(source.getT1(),
            source.getT2().getId(),
            source.getT2().getTitle());
    }
}
