package sabbat.apigateway.location.command;

import org.springframework.util.concurrent.ListenableFuture;
import rx.Observable;
import sabbat.apigateway.location.controller.converter.LocationApiDtoConverter;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.IActivityResponseDto;

/**
 * Created by bruenni on 04.08.16.
 */
public class UpdateActivityCommand implements ICommand {

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
        return this.ActivityRemoteService.update(dtoConverter.transformUpdateEvent(this.id, this.points));
    }

    @Override
    public IActivityResponseDto getDefault() {
        return null;
    }
}
