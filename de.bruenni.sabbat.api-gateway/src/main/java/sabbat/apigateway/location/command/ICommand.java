package sabbat.apigateway.location.command;

import sabbat.location.infrastructure.client.dto.IActivityResponseDto;

import java.util.concurrent.Future;

/**
 * Created by bruenni on 04.08.16.
 */
public interface ICommand {

    /**
     * Executes async.
     * @return
     */
    Future<? extends IActivityResponseDto> executeAsync();
}
