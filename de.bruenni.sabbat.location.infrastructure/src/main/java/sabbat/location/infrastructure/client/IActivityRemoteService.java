package sabbat.location.infrastructure.client;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import sabbat.location.infrastructure.client.dto.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by bruenni on 04.07.16.
 */
public interface IActivityRemoteService {

    /***
     * Creates new activity.
     * @param command
     * @return
     */
    CompletableFuture<ActivityCreatedResponseDto> start(ActivityCreateCommandDto command);

    /**
     * Stops activity
     * @param command
     * @return
     */
    CompletableFuture<ActivityStoppedResponseDto> stop(ActivityStopCommandDto command);

    /**
     * Update of activity with new geo points, heartrate and so on.
     * @param command
     * @return Future completes after update is send to infrfastructure to deliver measurement value.
     */
    CompletableFuture<Void> update(ActivityUpdateCommandDto command);

    /**
     * Echo ping pong method.
     * @param payload
     * @return
     */
    ListenableFuture<String> echo(@Payload String payload, @Header("authorization") String jwt);
}
