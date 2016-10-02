package sabbat.apigateway.location.command;

import org.slf4j.Logger;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import sabbat.apigateway.location.controller.MapMyTracksApiController;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by bruenni on 04.08.16.
 */
public class StartActivityCommandDummy implements ICommand {

    final Logger logger = org.slf4j.LoggerFactory.getLogger(StartActivityCommandDummy.class);

    private String id;

    /**
     *
     * @param id
     * @param title
     * @param points
     */
    public StartActivityCommandDummy(String id, String title, String points) {
        logger.debug("ctor" + id);
        this.id = id;
    }

    @Override
    public ListenableFuture<ActivityCreatedResponseDto> requestAsync() throws InterruptedException, ExecutionException, TimeoutException, IOException {

        // 1. authorize credentials credentials
        logger.debug(this.id);

        // 2. start activity
        ActivityCreatedResponseDto responseDto = new ActivityCreatedResponseDto(this.id);

        logger.debug(responseDto.toString());

        return new AsyncResult<>(responseDto);
    }

    @Override
    public void publish() throws Exception {
        throw new Exception("not implemented");
    }
}
