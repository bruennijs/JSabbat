package sabbat.location.infrastructure.builder;

import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bruenni on 08.10.16.
 */
public class ActivityCreateRequestDtoBuilder {
    public ActivityCreateRequestDto build() {
        AtomicInteger atomicInteger = new AtomicInteger(3647);
        Integer integer = new Integer(atomicInteger.getAndIncrement());

        return new ActivityCreateRequestDto(Long.valueOf(new Date().getTime()).toString(), "peterpan", "some title text of this track");
    }
}
