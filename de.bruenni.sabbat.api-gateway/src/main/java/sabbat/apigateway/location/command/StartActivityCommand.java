package sabbat.apigateway.location.command;

import infrastructure.identity.Token;
import infrastructure.parser.SerializingException;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import rx.Observable;
import sabbat.apigateway.location.controller.MapMyTracksApiController;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;
import sabbat.location.infrastructure.client.dto.IActivityResponseDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by bruenni on 04.08.16.
 */
public class StartActivityCommand implements ICommand {

    private static Logger log = LoggerFactory.getLogger(StartActivityCommand.class);
    private final String id;

    private IActivityRemoteService ActivityRemoteService;

    public void setActivityRemoteService(IActivityRemoteService activityRemoteService) {
        ActivityRemoteService = activityRemoteService;
    }

    private String title;
    private String points;

    public StartActivityCommand(String title, String points) {
        this.id = Long.valueOf(createNewUniqueId()).toString();
        this.title = title;
        this.points = points;
    }

    @Override
    public boolean getPublishOnly() {
        return false;
    }

    @Override
    public Observable<IActivityResponseDto> requestAsync() throws InterruptedException, ExecutionException, TimeoutException, SerializingException {

        // 0. get credentials by SecurityContextHolder
        infrastructure.identity.Token token = (infrastructure.identity.Token) SecurityContextHolder.getContext().getAuthentication().getDetails();

        if (log.isDebugEnabled())
        {
            log.debug(String.format("identity token [%1s]", token.getValue()));
        }

        // 1. transform to DTO
        ActivityCreateRequestDto dto = new ActivityCreateRequestDto(id, title, token.getValue());


        // 2. start activity
        return this.ActivityRemoteService.start(dto).map(resp -> resp);
    }

    @Override
    public Observable<Void> publish() throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public IActivityResponseDto getDefault() {
        return new ActivityCreatedResponseDto(this.id);
    }

    private long createNewUniqueId() {
        return new Date().getTime() % Integer.MAX_VALUE;
    }
}
