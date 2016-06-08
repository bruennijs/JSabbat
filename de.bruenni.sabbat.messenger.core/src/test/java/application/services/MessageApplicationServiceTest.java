package application.services;

import infrastructure.common.event.implementation.DomainEventBusImpl;
import org.junit.Test;
import sabbat.messenger.core.application.services.MessageApplicationService;

/**
 * Created by bruenni on 08.06.16.
 */
public class MessageApplicationServiceTest {
    @Test
    public void When_send_message_expect_messagedelivery_service_called()
    {
        MessageApplicationService sut = new MessageApplicationService(new DomainEventBusImpl<>());
        sut.send("olli", "meine neue nachricht");
    }
}
