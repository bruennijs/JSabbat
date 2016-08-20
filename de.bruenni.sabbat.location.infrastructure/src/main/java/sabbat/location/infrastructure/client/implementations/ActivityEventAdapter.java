package sabbat.location.infrastructure.client.implementations;

import org.springframework.messaging.Message;
import rx.Observable;
import rx.subjects.PublishSubject;
import sabbat.location.infrastructure.client.IActivityEventService;
import sabbat.location.infrastructure.client.dto.IActivityResponseDto;

/**
 * Created by bruenni on 20.08.16.
 */
public class ActivityEventAdapter implements IActivityEventService {
    private final PublishSubject<IActivityResponseDto> responseObservable;

    /**
     * Constructor
     */
    public ActivityEventAdapter() {
        this.responseObservable = PublishSubject.create();
    }

    public void onResponse(Message<String> message)
    {
        this.responseObservable.onNext(null);
    }

    @Override
    public Observable<IActivityResponseDto> OnResponse() {
        return this.responseObservable;
    }
}
