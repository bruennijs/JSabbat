package sabbat.apigateway.location.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import sabbat.location.api.dto.IActivityResponseDto;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruenni on 06.11.16.
 */
public class FaultTolerantCommandDecorator implements ICommand {

    private static Logger log = LoggerFactory.getLogger(FaultTolerantCommandDecorator.class);

    private long replyResponseTimeout = 5000;

    private ICommand decoratee;

    /**
     * Constructor
     * @param decoratee
     * @param timeOutInMs
     */
    public FaultTolerantCommandDecorator(ICommand decoratee, long timeOutInMs) {
        this.decoratee = decoratee;
        this.replyResponseTimeout = timeOutInMs;
    }

    @Override
    public boolean getPublishOnly() {
        return this.decoratee.getPublishOnly();
    }

    @Override
    public Observable<IActivityResponseDto> requestAsync() throws Exception {

        return this.decoratee.requestAsync()
                .timeout(replyResponseTimeout, TimeUnit.MILLISECONDS)
                .doOnError(exc -> { log.error(String.format("No reply received within timeout [timeoutInMs=%1s, exception=%2s]", replyResponseTimeout, exc.toString())); });
                //.doOnError(exc -> { log.error(String.format("No reply received within timeout but proceed [timeoutInMs=%1s, exception=%2s]", replyResponseTimeout, exc.toString())); })
                //.onErrorResumeNext(Observable.from(Arrays.asList(this.decoratee.getDefault())));
    }

    @Override
    public Observable<Void> publish() throws Exception {
        return null;
    }

    @Override
    public IActivityResponseDto getDefault() {
        return this.decoratee.getDefault();
    }
}
