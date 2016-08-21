package sabbat.location.infrastructure.client.implementations;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import rx.Observable;
import rx.subjects.PublishSubject;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static rx.subjects.PublishSubject.create;

/**
 * Created by bruenni on 04.07.16.
 */
public class RabbitMqActivityRemoteService implements IActivityRemoteService {

    private final PublishSubject<IActivityResponseDto> responseObservable;

    public RabbitMqActivityRemoteService() {
        this.responseObservable = PublishSubject.create();
    }

    @Override
    public ListenableFuture<ActivityCreatedResponseDto> start(ActivityCreateRequestDto command) {
        return new AsyncResult<ActivityCreatedResponseDto>(new ActivityCreatedResponseDto(command.getId()));
    }

    @Override
    public CompletableFuture<ActivityStoppedResponseDto> stop(ActivityStopRequestDto command) {
        CompletableFuture future = new CompletableFuture();
        future.complete(new ActivityStoppedResponseDto(command.getId()));
        return future;
    }

    @Override
    public CompletableFuture<Void> update(ActivityUpdateRequestDto command) {
        CompletableFuture future = new CompletableFuture();
        future.complete(null);
        return future;
    }

    @Override
    public ListenableFuture<String> echoAsync(@Payload String payload, @Header("authorization") String jwt) {
        AsyncResult<String> future = new AsyncResult<>("echo reply");
        return future;
    }

    /***
     *
     * @param message
     * @return
     */
    public String echo(Message<String> message)
    {
        return "RABBIT echo[" + message.getPayload() + "]";
    }
}
