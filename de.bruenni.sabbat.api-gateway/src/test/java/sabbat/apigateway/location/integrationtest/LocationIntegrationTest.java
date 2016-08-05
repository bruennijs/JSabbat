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
import rx.Observable;
import sabbat.apigateway.Application;
import sabbat.apigateway.location.config.WebConfig;
import sabbat.location.infrastructure.client.IActivityRemoteService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by bruenni on 14.07.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { IntegrationTestConfig.class, WebConfig.class, Application.class })
@WebIntegrationTest
public class LocationIntegrationTest {

    @Autowired
    public IActivityRemoteService ActivityRemoteService;

    @Test
    public void When_echo_activity_remote_service_expect_return_string_with_payload() throws ExecutionException, InterruptedException {
        String payload = "mypayloadtext";

        ListenableFuture<String> response = ActivityRemoteService.echo(payload, "tokenvalue");
        //Observable<String> observable = Observable.from(response);
        String result = response.get();

        Assert.assertTrue(StringFormatter.format("result doesnot contain payload [%s1]", result).getValue(), result.contains(payload));
    }
}
