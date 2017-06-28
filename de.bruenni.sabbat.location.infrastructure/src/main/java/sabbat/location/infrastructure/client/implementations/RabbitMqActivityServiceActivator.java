package sabbat.location.infrastructure.client.implementations;

import org.springframework.messaging.Message;
import sabbat.location.api.dto.*;

/**
 * Created by bruenni on 04.07.16.
 */
public class RabbitMqActivityServiceActivator {

    public ActivityCreatedResponseDto start(Message<ActivityCreateRequestDto> message) {
        return new ActivityCreatedResponseDto(message.getPayload().getId());
    }

    public ActivityStoppedResponseDto stop(Message<ActivityStopRequestDto> message) {
        return new ActivityStoppedResponseDto(message.getPayload().getId());
    }

    public void update(Message<ActivityUpdateEventDto> command) {
        return;
    }

    public String echo(Message<String> message)
    {
        return "RABBIT echo[" + message.getPayload() + "]";
    }
}
