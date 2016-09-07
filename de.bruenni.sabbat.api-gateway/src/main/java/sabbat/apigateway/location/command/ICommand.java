package sabbat.apigateway.location.command;

import sabbat.location.infrastructure.client.dto.IActivityResponseDto;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

/**
 * Created by bruenni on 04.08.16.
 */
public interface ICommand {

    /**
     * Executes async.
     * @return
     */
    Future<? extends IActivityResponseDto> executeAsync() throws InterruptedException, ExecutionException, TimeoutException, IOException;
}
