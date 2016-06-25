package application.services;

import builder.IdentityUserRepositoryBuilder;
import builder.MessageDeliveryServiceBuilder;
import builder.MessageRepositoryBuilder;
import builder.UserRepositoryBuilder;
import infrastructure.common.event.implementation.DomainEventBusImpl;
import org.junit.Test;
import org.mockito.verification.VerificationMode;
import sabbat.messenger.core.application.services.MessageApplicationService;
import sabbat.messenger.core.application.services.MessageSendCommand;
import sabbat.messenger.core.domain.messenger.aggregates.Message;
import sabbat.messenger.core.infrastructure.delivery.DeliveryRequest;
import sabbat.messenger.core.infrastructure.delivery.IMessageDeliveryService;
import sabbat.messenger.core.infrastructure.persistence.IMessageRepository;
import sabbat.messenger.core.infrastructure.persistence.IUserRepository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by bruenni on 08.06.16.
 */
public class MessageApplicationServiceTest {
    @Test
    public void When_send_message_expect_messagedelivery_service_called() throws ExecutionException, InterruptedException {
        String userFrom = "olli";
        String userTo = "peter";

        IUserRepository userRepo = new UserRepositoryBuilder()
                .WithFindByUserName(userFrom, new IdentityUserRepositoryBuilder().Build())
                .WithFindByUserName(userTo, new IdentityUserRepositoryBuilder().Build())
                .BuildMocked();
        IMessageRepository msgRepo = new MessageRepositoryBuilder().BuildMocked();

        IMessageDeliveryService messageDeliveryService = new MessageDeliveryServiceBuilder().BuildMocked();

        MessageApplicationService sut = new MessageApplicationService(
                new DomainEventBusImpl(), msgRepo, userRepo, messageDeliveryService);

        CompletableFuture<Message> sendFuture = sut.send(new MessageSendCommand("peter", "olli", "meine neue nachricht"));

        Message message = sendFuture.get();

        verify(messageDeliveryService).requestDelivery(argThat(argument -> ((DeliveryRequest)argument).getCorrelationId().equals(message.getId().toString())));
    }
}
