package sabbat.location.infrastructure.builder;

import infrastructure.identity.ITokenAuthentication;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bruenni on 08.10.16.
 */
public class ActivityCreateRequestDtoBuilder extends ActivityRequestDtoBuilderBase {

    public ActivityCreateRequestDto build() {

        return new ActivityCreateRequestDto(Long.valueOf(new Date().getTime()).toString(),
                "title.infrastructure.test",
                this.identityToken);
    }
}
