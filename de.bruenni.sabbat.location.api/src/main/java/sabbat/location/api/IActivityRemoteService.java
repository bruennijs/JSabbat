package sabbat.location.api;

import infrastructure.parser.SerializingException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.concurrent.ListenableFuture;
import sabbat.location.api.dto.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by bruenni on 04.07.16.
 */
public interface IActivityRemoteService {

    /***
     * Creates new activity.
     * @param command
     * @return
     */
    rx.Observable<ActivityCreatedResponseDto> start(@Payload ActivityCreateRequestDto command) throws InterruptedException, ExecutionException, TimeoutException, SerializingException;

    /**
     * Stops activity
     * @param command
     * @return
     */
    rx.Observable<ActivityStoppedResponseDto> stop(ActivityStopRequestDto command);

    /**
     * Update of activity with new geo points, heartrate and so on.
     * @param command
     * @return Observable publishing channel confirmation whether dto has been
     */
    rx.Observable<Void> update(ActivityUpdateEventDto command) throws Exception;

    /**
     * Echo ping pong method.
     * @param payload
     * @return
     */
    ListenableFuture<String> echoAsync(@Payload String payload, @Header("authorization") String jwt);
}
