package sabbat.apigateway.location.command;

import infrastructure.identity.Token;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import rx.Observable;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by bruenni on 04.08.16.
 */
public class StartActivityCommand implements ICommand {

    private static Logger log = LoggerFactory.getLogger(StartActivityCommand.class);

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
    public Observable<ActivityCreatedResponseDto> requestAsync() throws InterruptedException, ExecutionException, TimeoutException, IOException {

        // 1. get credentials by SecurityContextHolder
        infrastructure.identity.Token token = (infrastructure.identity.Token) SecurityContextHolder.getContext().getAuthentication().getDetails();

        if (log.isDebugEnabled())
        {
            log.debug(String.format("identity token [%1s]", token.getValue()));
        }

        // 2. start activity
        return this.ActivityRemoteService.start(transformStartRequest(title, points, token));
    }

    @Override
    public Observable<Void> publish() throws Exception {
        throw new Exception("not implemented");
    }

    /***
     * Transforms controller request to IActivityApplicationService command.
     *
     * @param title
     * @param token
     * @return
     */
    private ActivityCreateRequestDto transformStartRequest(String title, String points, Token token)
    {
        return new ActivityCreateRequestDto(id, title, token.getValue());
    }
}
