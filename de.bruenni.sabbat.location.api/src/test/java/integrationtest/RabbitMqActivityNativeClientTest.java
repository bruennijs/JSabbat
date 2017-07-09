package integrationtest;

import builder.ActivityCreateRequestDtoBuilder;
import builder.ActivityUpdateEventDtoBuilder;
import builder.TimeSeriesCoordinateBuilder;
import infrastructure.parser.SerializingException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rx.Observable;
import sabbat.location.api.AmqpClientAutoConfiguration;
import sabbat.location.api.dto.ActivityCreateRequestDto;
import sabbat.location.api.dto.ActivityCreatedResponseDto;
import sabbat.location.api.dto.ActivityUpdateEventDto;
import sabbat.location.api.dto.TimeSeriesCoordinate;
import sabbat.location.api.implementations.RabbitMqActivityRemoteService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by bruenni on 28.08.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"dev"})
@SpringBootTest(classes =
        {
                IntegrationTestConfig.class,
                AmqpClientAutoConfiguration.class
        })
//@Ignore
public class RabbitMqActivityNativeClientTest {

    @Value("${spring.rabbitmq.host}")
    public String host;

    @Autowired
    public RabbitMqActivityRemoteService Client;

    @Test
    public void When_start_activity_expect_message_send_to_exchange() throws IOException, InterruptedException, ExecutionException, TimeoutException, SerializingException {

        //ExecutorService executorService = Executors.newFixedThreadPool(5);

        ActivityCreateRequestDto dto = new ActivityCreateRequestDtoBuilder().build();

        Observable<ActivityCreatedResponseDto> startObservable = Client.start(dto, "tokenvalue");
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
