package sabbat.location.infrastructure.client;

import sabbat.location.infrastructure.client.dto.IActivityResponseDto;

/**
 * Created by bruenni on 20.08.16.
 */
public interface IActivityEventService {

    /**
     * Observable pusblished on response received.
     * @return
     */
    rx.Observable<IActivityResponseDto> OnResponse();
}
