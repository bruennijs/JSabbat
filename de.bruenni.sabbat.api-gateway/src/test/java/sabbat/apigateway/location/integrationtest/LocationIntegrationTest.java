package sabbat.apigateway.location.integrationtest;

import com.sun.javafx.binding.StringFormatter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.concurrent.ListenableFuture;
import rx.observables.BlockingObservable;
import sabbat.apigateway.Application;
import sabbat.apigateway.location.config.WebConfig;
import sabbat.location.infrastructure.client.IActivityEventService;
import sabbat.location.infrastructure.client.IActivityRemoteService;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;
import sabbat.location.infrastructure.client.dto.IActivityResponseDto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bruenni on 14.07.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { IntegrationTestConfig.class, WebConfig.class, Application.class })
@WebIntegrationTest
public class LocationIntegrationTest {

    @Autowired
    public IActivityRemoteService ActivityRemoteService;

    @Autowired
    public IActivityEventService ActivityEventService;

    @Test
    public void When_echo_activity_remote_service_expect_return_string_with_payload() throws ExecutionException, InterruptedException {
        String payload = "mypayloadtext";

        Future<String> response = ActivityRemoteService.echoAsync(payload, "tokenvalue");
        //Observable<String> observable = Observable.from(response);
        String result = response.get();

        Assert.assertTrue(StringFormatter.format("result does not contain payload [%s1]", result).getValue(), result.contains(payload));
    }

    @Test
    public void when_send_ActivityCreateRequest_expect_response_returned() throws Exception {

        AtomicInteger atomicInteger = new AtomicInteger(3647);

        Integer integer = new Integer(atomicInteger.getAndIncrement());
        CompletableFuture<ActivityCreatedResponseDto> future = ActivityRemoteService.start(new ActivityCreateRequestDto(integer.toString(), "some title text of this track"));
        ActivityCreatedResponseDto responseDto = future.get();
        Assert.assertEquals(integer.intValue(), Integer.decode(responseDto.getId()).intValue());
    }

    @Test
    public void when_send_ActivityCreateRequest_expect_IActivityEventService_received_same_event() throws Exception {

        AtomicInteger atomicInteger = new AtomicInteger(3647);
        BlockingObservable<IActivityResponseDto> eventObs = ActivityEventService.OnResponse().take(1).timeout(1000, TimeUnit.MILLISECONDS).toBlocking();

        Integer integer = new Integer(atomicInteger.getAndIncrement());

        CompletableFuture<ActivityCreatedResponseDto> future = ActivityRemoteService.start(new ActivityCreateRequestDto(integer.toString(), "some title text of this track"));
        ActivityCreatedResponseDto responseDto = future.get();
        // get respons eform event
        IActivityResponseDto eventResponseDto = eventObs.first();


        Assert.assertEquals("Event response and response of remote call of same instance", eventResponseDto.getClass(), ActivityCreatedResponseDto.class);
        ActivityCreatedResponseDto eventCreatedResponseDto = (ActivityCreatedResponseDto) eventResponseDto;

        Assert.assertEquals(responseDto.getId(), eventCreatedResponseDto.getId());
    }
}
