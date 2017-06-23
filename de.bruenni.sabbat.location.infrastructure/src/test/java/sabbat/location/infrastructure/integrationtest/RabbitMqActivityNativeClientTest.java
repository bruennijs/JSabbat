package sabbat.location.infrastructure.integrationtest;

import infrastructure.parser.SerializingException;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import sabbat.location.infrastructure.AmqpClientAutoConfiguration;
import sabbat.location.infrastructure.builder.ActivityCreateRequestDtoBuilder;
import sabbat.location.infrastructure.builder.ActivityUpdateEventDtoBuilder;
import sabbat.location.infrastructure.builder.TimeSeriesCoordinateBuilder;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;
import sabbat.location.infrastructure.client.dto.ActivityUpdateEventDto;
import sabbat.location.infrastructure.client.dto.TimeSeriesCoordinate;
import sabbat.location.infrastructure.client.implementations.RabbitMqActivityRemoteService;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * Created by bruenni on 28.08.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"dev"})
@SpringApplicationConfiguration(classes =
        {
                IntegrationTestConfig.class,
                AmqpClientAutoConfiguration.class
        })
public class RabbitMqActivityNativeClientTest {

    @Value("${spring.rabbitmq.host}")
    public String host;

    @Autowired
    public RabbitMqActivityRemoteService Client;

    @Test
    public void When_start_activity_expect_message_send_to_exchange() throws IOException, InterruptedException, ExecutionException, TimeoutException, SerializingException {

        //ExecutorService executorService = Executors.newFixedThreadPool(5);

        ActivityCreateRequestDto dto = new ActivityCreateRequestDtoBuilder().build();

        Observable<ActivityCreatedResponseDto> startObservable = Client.start(dto);
        ActivityCreatedResponseDto response = startObservable.timeout(5000, TimeUnit.MILLISECONDS).toBlocking().single();

        Assert.assertEquals(dto.getId(), response.getId());
    }

    @Test
    public void When_update_activity_expect_message_send_to_exchange() throws Exception {

        TimeSeriesCoordinate timeSeriesCoordinate = new TimeSeriesCoordinateBuilder().withLatitude(179.99).build();
        ActivityUpdateEventDto dto = new ActivityUpdateEventDtoBuilder()
                .withActivityId("activity:id")
                .withTimeSeries(timeSeriesCoordinate)
                .build();

        Observable<Void> observable = Client.update(dto);

        observable.timeout(5000, TimeUnit.MILLISECONDS).toBlocking().single();
    }
}
