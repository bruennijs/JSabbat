package sabbat.location.infrastructure.client.implementations;

import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import java.util.Optional;

/**
 * Created by bruenni on 05.08.16.
 */
public class EchoServiceActivator {
    public String compute(Optional<String> payload,
                          @Header(value="authorization", required=false) String jwt) {
        if (payload.isPresent()) {
            String value = payload.get();
        }

        return "reply [payload=" + payload;
    }

    public String compute(Message<String> message) {

        String authorization = "notset";
        String payload = message.getPayload();

        if (message.getHeaders().containsKey("authorization")){
            authorization = (String)message.getHeaders().get("authorization");
        }

        return "echo reply=" + payload;
    }
}
