package sabbat.apigateway.location.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import rx.Observable;
import sabbat.apigateway.location.controller.converter.LocationApiDtoConverter;
import sabbat.location.api.IActivityRemoteService;
import sabbat.location.api.dto.ActivityUpdateEventDto;
import sabbat.location.api.dto.IActivityResponseDto;

/**
 * Created by bruenni on 04.08.16.
 */
public class UpdateActivityCommand implements ICommand {

    private static Logger log = LoggerFactory.getLogger(UpdateActivityCommand.class);

    private IActivityRemoteService ActivityRemoteService;
    private LocationApiDtoConverter dtoConverter;

    public void setActivityRemoteService(IActivityRemoteService activityRemoteService) {
        ActivityRemoteService = activityRemoteService;
    }

    public void setDtoConverter(LocationApiDtoConverter dtoConverter) {
        this.dtoConverter = dtoConverter;
    }

    private String id;
    private String points;

    /**
     * Constructor
     * @param id
     * @param points
     */
    public UpdateActivityCommand(String id, String points) {
        this.id = id;
        this.points = points;
    }

    @Override
    public boolean getPublishOnly() {
        return true;
    }

    @Override
    public Observable<IActivityResponseDto> requestAsync() throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public Observable<Void> publish() throws Exception {

        ActivityUpdateEventDto dto = dtoConverter.transformUpdateEvent(this.id, this.points);

        infrastructure.identity.Token token = (infrastructure.identity.Token) SecurityContextHolder.getContext().getAuthentication().getDetails();

        dto.setIdentityToken(token.getValue());

        return this.ActivityRemoteService.update(dto);
    }

    @Override
    public IActivityResponseDto getDefault() {
        return null;
    }
}
