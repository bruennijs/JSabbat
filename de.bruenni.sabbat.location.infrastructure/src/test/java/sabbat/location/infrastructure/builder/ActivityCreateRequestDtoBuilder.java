package sabbat.location.infrastructure.builder;


import sabbat.location.api.dto.ActivityCreateRequestDto;

import java.util.Date;

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
