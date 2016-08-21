package sabbat.location.infrastructure.client.implementations;

import org.springframework.messaging.Message;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import sabbat.location.infrastructure.client.IActivityEventService;
import sabbat.location.infrastructure.client.dto.IActivityResponseDto;

/**
 * Created by bruenni on 20.08.16.
 */
public class ActivityEventAdapter implements IActivityEventService {
    private final ReplaySubject<IActivityResponseDto> responseObservable;

    /**
     * Constructor
     */
    public ActivityEventAdapter()
    {
        this.responseObservable = ReplaySubject.create();
    }

    public void onResponse(Message<IActivityResponseDto> message)
    {
        this.responseObservable.onNext(message.getPayload());
    }

    @Override
    public Observable<IActivityResponseDto> OnResponse() {
        return this.responseObservable;
    }
}
