package sabbat.location.infrastructure.client.implementations;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import rx.Observable;
import rx.subjects.PublishSubject;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.*;

import java.util.concurrent.CompletableFuture;

/**
 * Created by bruenni on 04.07.16.
 */
public class RabbitMqActivityServiceActivator {

    public ActivityCreatedResponseDto start(Message<ActivityCreateRequestDto> message) {
        return new ActivityCreatedResponseDto(message.getPayload().getId());
    }

    public ActivityStoppedResponseDto stop(Message<ActivityStopRequestDto> message) {
        return new ActivityStoppedResponseDto(message.getPayload().getId());
    }

    public void update(Message<ActivityUpdateEventDto> command) {
        return;
    }

    public String echo(Message<String> message)
    {
        return "RABBIT echo[" + message.getPayload() + "]";
    }
}
