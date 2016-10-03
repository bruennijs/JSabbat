package sabbat.apigateway.location.command;

import org.springframework.util.concurrent.ListenableFuture;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;
import sabbat.location.infrastructure.client.dto.ActivityStopRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityStoppedResponseDto;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by bruenni on 04.08.16.
 */
public class StopActivityCommand implements ICommand {

    private IActivityRemoteService ActivityRemoteService;

    public void setActivityRemoteService(IActivityRemoteService activityRemoteService) {
        ActivityRemoteService = activityRemoteService;
    }

    private String id;

    public StopActivityCommand(String id) {
        this.id = id;
    }

    @Override
    public boolean getPublishOnly() {
        return false;
    }

    @Override
    public ListenableFuture<ActivityStoppedResponseDto> requestAsync() throws InterruptedException, ExecutionException, TimeoutException, IOException {

        // 1. authorize credentials credentials


        // 2. start activity
        return this.ActivityRemoteService.stop(transformStartRequest(this.id));
    }

    @Override
    public void publish() throws Exception {
        throw new Exception("not implemented");
    }

    /***
     * Transforms controller request to IActivityApplicationService command.
     *
     * @return
     */
    private ActivityStopRequestDto transformStartRequest(String id)
    {
        return new ActivityStopRequestDto(id);
    }
}
