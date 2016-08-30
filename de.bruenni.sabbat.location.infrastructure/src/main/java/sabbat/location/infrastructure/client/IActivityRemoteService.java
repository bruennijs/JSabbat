package sabbat.location.infrastructure.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.concurrent.ListenableFuture;
import sabbat.location.infrastructure.client.dto.*;

import java.util.concurrent.CompletableFuture;

/**
 * Created by bruenni on 04.07.16.
 */
public interface IActivityRemoteService {

    /***
     * Creates new activity.
     * @param command
     * @return
     */
    ListenableFuture<ActivityCreatedResponseDto> start(@Payload ActivityCreateRequestDto command) throws JsonProcessingException;

    /**
     * Stops activity
     * @param command
     * @return
     */
    CompletableFuture<ActivityStoppedResponseDto> stop(ActivityStopRequestDto command);

    /**
     * Update of activity with new geo points, heartrate and so on.
     * @param command
     * @return Future completes after update is send to infrfastructure to deliver measurement value.
     */
    CompletableFuture<Void> update(ActivityUpdateRequestDto command);

    /**
     * Echo ping pong method.
     * @param payload
     * @return
     */
    ListenableFuture<String> echoAsync(@Payload String payload, @Header("authorization") String jwt);
}
