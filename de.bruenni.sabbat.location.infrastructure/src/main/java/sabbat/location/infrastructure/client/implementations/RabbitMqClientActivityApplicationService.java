package sabbat.location.infrastructure.client.implementations;

import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by bruenni on 04.07.16.
 */
public class RabbitMqClientActivityApplicationService implements IActivityRemoteService {

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
}
