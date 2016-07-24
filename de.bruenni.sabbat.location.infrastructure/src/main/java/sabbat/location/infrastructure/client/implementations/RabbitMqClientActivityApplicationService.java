package sabbat.location.infrastructure.client.implementations;

import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.ActivityCreateCommandDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;
import sabbat.location.infrastructure.client.dto.ActivityStopCommandDto;
import sabbat.location.infrastructure.client.dto.ActivityUpdateCommandDto;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by bruenni on 04.07.16.
 */
public class RabbitMqClientActivityApplicationService implements IActivityRemoteService {

    @Override
    public Future<ActivityCreatedResponseDto> start(ActivityCreateCommandDto command) {
        CompletableFuture future = new CompletableFuture();
        future.complete(new ActivityCreatedResponseDto(UUID.randomUUID().toString()));
        return future;
    }

    @Override
    public Future<Void> stop(ActivityStopCommandDto command) {
        CompletableFuture future = new CompletableFuture();
        future.complete(null);
        return future;
    }

    @Override
    public Future<Void> update(ActivityUpdateCommandDto command) {
        CompletableFuture future = new CompletableFuture();
        future.complete(null);
        return future;
    }
}
