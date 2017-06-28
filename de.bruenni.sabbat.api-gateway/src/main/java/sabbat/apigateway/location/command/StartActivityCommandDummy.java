package sabbat.apigateway.location.command;

import org.slf4j.Logger;
import rx.Observable;
import rx.subjects.ReplaySubject;
import sabbat.location.api.dto.ActivityCreatedResponseDto;
import sabbat.location.api.dto.IActivityResponseDto;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by bruenni on 04.08.16.
 */
public class StartActivityCommandDummy implements ICommand {

    static long idCounter = 1;

    final Logger logger = org.slf4j.LoggerFactory.getLogger(StartActivityCommandDummy.class);

    private String id;

    /**
     * @param title
     * @param points
     */
    public StartActivityCommandDummy(String title, String points) {

        this.id = getNextId();
    }

    @Override
    public boolean getPublishOnly() {
        return false;
    }

    @Override
    public Observable<IActivityResponseDto> requestAsync() throws InterruptedException, ExecutionException, TimeoutException, IOException {

        // 1. authorize credentials credentials
        //logger.debug(this.id);

        // 2. start activity
        ActivityCreatedResponseDto responseDto = new ActivityCreatedResponseDto(this.id);

        //logger.debug(responseDto.toString());

        ReplaySubject<ActivityCreatedResponseDto> subject = ReplaySubject.create();
        subject.onNext(responseDto);
        return subject.map(resp -> resp);
    }

    @Override
    public Observable<Void> publish() throws Exception {
        throw new Exception("not implemented");
    }

    @Override
    public IActivityResponseDto getDefault() {
        return null;
    }

    public String getNextId() {
        idCounter++;
        return Long.valueOf(idCounter).toString();
    }
}
