package sabbat.apigateway.location.command;

import org.springframework.util.concurrent.ListenableFuture;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;

import java.util.concurrent.CompletableFuture;

/**
 * Created by bruenni on 04.08.16.
 */
public class StartActivityCommand implements ICommand {

    public IActivityRemoteService ActivityRemoteService;

    public void setActivityRemoteService(IActivityRemoteService activityRemoteService) {
        ActivityRemoteService = activityRemoteService;
    }

    private String id;
    private String title;
    private String points;

    public StartActivityCommand(String id, String title, String points) {
        this.id = id;
        this.title = title;
        this.points = points;
    }

    @Override
    public ListenableFuture<ActivityCreatedResponseDto> executeAsync() {

        // 1. authorize credentials credentials


        // 2. start activity
        return this.ActivityRemoteService.start(transformStartRequest(title, points));
    }

    /***
     * Transforms controller request to IActivityService command.
     *
     * @param title
     * @return
     */
    private ActivityCreateRequestDto transformStartRequest(String title, String points)
    {
        return new ActivityCreateRequestDto(id, title);
    }
}