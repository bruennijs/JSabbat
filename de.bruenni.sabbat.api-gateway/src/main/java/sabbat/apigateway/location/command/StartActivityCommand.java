package sabbat.apigateway.location.command;

import org.springframework.util.concurrent.ListenableFuture;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by bruenni on 04.08.16.
 */
public class StartActivityCommand implements ICommand {

    private IActivityRemoteService ActivityRemoteService;

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
    public boolean getPublishOnly() {
        return false;
    }

    @Override
    public ListenableFuture<ActivityCreatedResponseDto> requestAsync() throws InterruptedException, ExecutionException, TimeoutException, IOException {

        // 1. authorize credentials credentials


        // 2. start activity
        return this.ActivityRemoteService.start(transformStartRequest(title, points));
    }

    @Override
    public void publish() throws Exception {
        throw new Exception("not implemented");
    }

    /***
     * Transforms controller request to IActivityApplicationService command.
     *
     * @param title
     * @return
     */
    private ActivityCreateRequestDto transformStartRequest(String title, String points)
    {
        return new ActivityCreateRequestDto(id, title);
    }
}
