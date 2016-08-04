package sabbat.apigateway.location.command;

import org.springframework.beans.factory.annotation.Autowired;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.ActivityCreateCommandDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;
import sabbat.location.infrastructure.client.dto.IActivityResponseDto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by bruenni on 04.08.16.
 */
public class StartActivityCommand implements ICommand {

    public IActivityRemoteService ActivityRemoteService;

    public void setActivityRemoteService(IActivityRemoteService activityRemoteService) {
        ActivityRemoteService = activityRemoteService;
    }

    private String title;
    private String points;

    public StartActivityCommand(String title, String points) {
        this.title = title;
        this.points = points;
    }

    @Override
    public CompletableFuture<ActivityCreatedResponseDto> executeAsync() {

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
    private ActivityCreateCommandDto transformStartRequest(String title, String points)
    {
        return new ActivityCreateCommandDto(title);
    }
}
