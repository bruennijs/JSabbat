package sabbat.location.infrastructure.client.implementations;

import org.springframework.expression.spel.ast.StringLiteral;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureTask;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by bruenni on 04.07.16.
 */
public class RabbitMqActivityRemoteService implements IActivityRemoteService {

    public RabbitMqActivityRemoteService() {
    }

    @Override
    public CompletableFuture<ActivityCreatedResponseDto> start(ActivityCreateCommandDto command) {
        CompletableFuture future = new CompletableFuture();
        //future.complete(new ActivityCreatedResponseDto(UUID.randomUUID().toString().trim()));
        future.complete(new ActivityCreatedResponseDto("4536"));
        return future;
    }

    @Override
    public CompletableFuture<ActivityStoppedResponseDto> stop(ActivityStopCommandDto command) {
        CompletableFuture future = new CompletableFuture();
        future.complete(new ActivityStoppedResponseDto());
        return future;
    }

    @Override
    public CompletableFuture<Void> update(ActivityUpdateCommandDto command) {
        CompletableFuture future = new CompletableFuture();
        future.complete(null);
        return future;
    }

    @Override
    public ListenableFuture<String> echo(@Payload String payload, @Header("authorization") String jwt) {
        AsyncResult<String> future = new AsyncResult<>("echo reply");
        return future;
    }
}
