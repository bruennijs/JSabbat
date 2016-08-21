package sabbat.location.infrastructure.integrationtest.stubs;

import org.springframework.messaging.Message;
import sabbat.location.infrastructure.client.dto.*;

/**
 * Created by bruenni on 04.07.16.
 */
public class ActivityStubServiceActivator {

    public ActivityCreatedResponseDto start(Message<ActivityCreateRequestDto> message) {
        return new ActivityCreatedResponseDto(message.getPayload().getId());
    }

    public ActivityStoppedResponseDto stop(Message<ActivityStopRequestDto> message) {
        return new ActivityStoppedResponseDto(message.getPayload().getId());
    }

    public void update(Message<ActivityUpdateRequestDto> command) {
        return;
    }

    public String echo(Message<String> message)
    {
        return "RABBIT echo[" + message.getPayload() + "]";
    }
}
