package sabbat.location.infrastructure.integrationtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.w3c.dom.events.UIEvent;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;
import sabbat.location.infrastructure.client.implementations.RabbitMqActivityNativeClient;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by bruenni on 28.08.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { AmqpClientTestConfig.class })
public class RabbitMqActivityNativeClientTest {

    @Autowired
    public RabbitMqActivityNativeClient Client;

    @Test
    public void When_start_activity_expect_message_send_to_exchange() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        ActivityCreateRequestDto dto = new ActivityCreateRequestDto(UUID.randomUUID().toString(), "mein erstes Rennen");

        ListenableFuture<ActivityCreatedResponseDto> responseFuture = Client.start(dto);
        ActivityCreatedResponseDto response = responseFuture.get(1000, TimeUnit.MILLISECONDS);

        Assert.assertEquals(dto.getId(), response.getId());
    }
}
