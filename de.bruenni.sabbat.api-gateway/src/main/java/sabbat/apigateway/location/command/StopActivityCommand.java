package sabbat.apigateway.location.command;

import infrastructure.parser.SerializingException;
import org.springframework.security.core.context.SecurityContextHolder;
import rx.Observable;
import sabbat.location.api.IActivityRemoteService;
import sabbat.location.api.dto.ActivityStopRequestDto;
import sabbat.location.api.dto.ActivityStoppedResponseDto;
import sabbat.location.api.dto.IActivityResponseDto;

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
    public Observable<IActivityResponseDto> requestAsync() throws InterruptedException, ExecutionException, TimeoutException, IOException, SerializingException {

        // 1. get credentials
        infrastructure.identity.Token token = (infrastructure.identity.Token) SecurityContextHolder.getContext().getAuthentication().getDetails();

        // 2. start activity
        return this.ActivityRemoteService.stop(transformStopRequest(this.id), token.getValue()).map(resp -> resp);
    }

    @Override
    public Observable<Void> publish() throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public IActivityResponseDto getDefault() {
        return new ActivityStoppedResponseDto(this.id);
    }

    /***
     * Transforms controller request to ActivityApplicationService command.
     *
     * @return
     */
    private ActivityStopRequestDto transformStopRequest(String id)
    {
        return new ActivityStopRequestDto(id, "");
    }
}
