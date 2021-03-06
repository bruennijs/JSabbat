package sabbat.location.infrastructure.integrationtest;

import com.sun.javafx.binding.StringFormatter;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rx.Observable;
import rx.observables.BlockingObservable;
import sabbat.location.api.IActivityRemoteService;
import sabbat.location.api.dto.ActivityCreatedResponseDto;
import sabbat.location.api.dto.IActivityResponseDto;
import sabbat.location.infrastructure.builder.ActivityCreateRequestDtoBuilder;
import sabbat.location.infrastructure.client.IActivityEventService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruenni on 14.07.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"test"})
@SpringBootTest(classes = { IntegrationTestConfig.class })
public class LocationIntegrationTest {

    Logger logger = org.slf4j.LoggerFactory.getLogger("location.infrastructure.traffic");

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
    public void when_send_ActivityCreateRequest_expect_IActivityEventService_received_same_event() throws Exception {

        BlockingObservable<IActivityResponseDto> eventObs = ActivityEventService.OnResponse()
                .doOnNext(resp -> logger.debug(resp.toString()))
                .take(1)
                .timeout(2000, TimeUnit.MILLISECONDS)
                .toBlocking();

        Observable<ActivityCreatedResponseDto> startObservable = ActivityRemoteService.start(new ActivityCreateRequestDtoBuilder().build(), "tokenvalue");
        ActivityCreatedResponseDto responseDto = startObservable.timeout(5000, TimeUnit.MILLISECONDS).toBlocking().single();
        // get respons eform event
        IActivityResponseDto eventResponseDto = eventObs.first();

        Assert.assertEquals("Event response and response of remote call of same instance", eventResponseDto.getClass(), ActivityCreatedResponseDto.class);
        ActivityCreatedResponseDto eventCreatedResponseDto = (ActivityCreatedResponseDto) eventResponseDto;

        Assert.assertEquals(responseDto.getId(), eventCreatedResponseDto.getId());
    }
}
