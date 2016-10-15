package sabbat.apigateway.location.command;

import rx.Observable;
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
     * Flag checking whether only publish is supported
     */
    boolean getPublishOnly();

    /**
     * Executes async.
     * @return
     */
    Observable<? extends IActivityResponseDto> requestAsync() throws Exception;

    /**
     * Publishes an event without service response synchronously.
     * @return
     */
    Observable<Void> publish() throws Exception;
}
